package projekti.logic.service;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import projekti.domain.Account;
import projekti.logic.repository.AccountRepository;
import projekti.logic.utility.CustomDate;
import projekti.security.PasswordConstraintValidator;

@Service
public class RegisterService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomDate date;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Checks password validity
    // Credits to https://www.baeldung.com/registration-password-strength-and-rules
    @Documented
    @Constraint(validatedBy = PasswordConstraintValidator.class)
    @Target({TYPE, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    public @interface ValidPassword {

        String message() default "Invalid Password";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }

    // Allows only numbers, uppercase letters, lowercase letters, space (because real names have spaces), &, @, _
    public String convertRegisterEntry(String convert) {
        String converted = "";
        for (int i = 0; i < convert.length(); i++) {
            char c = convert.charAt(i);
            if (((int) c >= 48 && (int) c <= 57)
                    || ((int) c >= 65 && (int) c <= 90)
                    || ((int) c >= 97 && (int) c <= 122)
                    || ((int) c == 32)
                    || ((int) c == 38)
                    || ((int) c == 64)
                    || ((int) c == 95)) {
                converted += c;
            } else {
                converted = "ERROR";
                return converted;
            }
        }
        return converted;
    }

    // Returns a string without spaces of parameter string
    public String convertRemoveSpaces(String convert) {
        String converted = convert;
        converted = converted.trim().replaceAll("\\s+", "");
        return converted;
    }

    // Creates a new account for username and useralias if username or useralias is not reserved and form entries are acceptable
    public String registerCheck(Model model,
            @Valid @ModelAttribute Account account,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("date", this.date.date());
            return "register";
        }
        if (this.accountRepository.findByUsername(account.getUsername())
                != null
                || account.getUsername().equals("anonymousUser")
                || account.getUsername().equals("null")) {
            model.addAttribute("date", this.date.date());
            model.addAttribute("username", account.getUsername());
            model.addAttribute("usernameFail", "");
            return "register_error";
        }
        if (!account.getPassword().equals(account.getConfirm())) {
            model.addAttribute("confirmFail", "");
            model.addAttribute("date", this.date.date());
            model.addAttribute("username", account.getUsername());
            return "register_error";
        }
        if (this.accountRepository.findByUseralias(account.getUseralias()) != null) {
            model.addAttribute("date", this.date.date());
            model.addAttribute("useralias", account.getUseralias());
            model.addAttribute("useraliasFail", "");
            model.addAttribute("username", account.getUsername());
            return "register_error";
        } else {
            String username = convertRegisterEntry(convertRemoveSpaces(account.getUsername()));
            String password = convertRegisterEntry(account.getPassword());
            String confirm = convertRegisterEntry(account.getConfirm());
            String realname = convertRegisterEntry(account.getRealname());
            String useralias = convertRegisterEntry(convertRemoveSpaces(account.getUseralias()));
            if (username.equals("ERROR")
                    || (username.length() < account.getUsername().length())
                    || password.equals("ERROR")
                    || realname.equals("ERROR")
                    || useralias.equals("ERROR")
                    || (useralias.length() < account.getUseralias().length())) {
                model.addAttribute("date", this.date.date());
                model.addAttribute("entryFail", "");
                return "register_error";
            }
            account.setUsername(username);
            account.setPassword(passwordEncoder.encode(password));
            account.setConfirm(confirm);
            account.setRealname(realname);
            account.setUseralias(useralias);
        }
        this.accountRepository.save(account);
        return "redirect:/EmployeeSearch/Register/" + account.getUseralias();
    }
}
