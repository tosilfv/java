package projekti.logic.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.domain.Ability;
import projekti.domain.Account;
import projekti.domain.Praise;
import projekti.logic.repository.AbilityRepository;
import projekti.logic.repository.AccountRepository;
import projekti.logic.repository.PraiseRepository;

@Service
public class AbilityService {

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PraiseRepository praiseRepository;

    // After calling deletePraises method deletes parameter ability from repository if ability was created by parameter user
    @Transactional
    public void deleteAbility(String useralias, Ability ability) {
        if (ability.getAccount().getUseralias().equals(useralias)) {
            List<Praise> praisers = ability.getPraisers();
            deletePraises(praisers);
            this.abilityRepository.delete(ability);
        }
    }

    // Deletes parameter praises from repository
    @Transactional
    public void deletePraises(List<Praise> praisers) {
        for (Praise p : praisers) {
            this.praiseRepository.delete(p);
        }
    }

    // Adds new ability to account of parameter useralias
    public void newAbility(String useralias, String abilitytext) {
        Account account = this.accountRepository.findByUseralias(useralias);
        Ability ability = new Ability();
        ability.setAbilitytext(abilitytext);
        ability.setAccount(account);
        ability.setPraises(0);
        this.abilityRepository.save(ability);
    }

    // Adds new praise to parameter ability and increments the praises integer of parameter ability if it's not there yet
    public void newPraise(Ability ability, String useralias, String praisetext, Account visitingAccount) {
        List<Praise> praisers = ability.getPraisers();
        for (Praise p : praisers) {
            if (p.getUseralias().equals(useralias)) {
                return;
            }
        }
        Praise praise = new Praise();
        praise.setUseralias(useralias);
        praise.setPraisetext(praisetext);
        praise.setAccount(visitingAccount);
        praise.setAbility(ability);
        this.praiseRepository.save(praise);
        ability.setPraises(ability.getPraises() + 1);
        this.abilityRepository.save(ability);
    }

    // Returns s list of abilities, which is in a reversed order compared to the parameter list
    public List<Ability> reverseListOrder(List<Ability> abilityList) {
        abilityList.sort(Comparator.comparing(s -> s.getPraises()));
        Collections.reverse(abilityList);
        List<Ability> reversedList = new ArrayList<>();
        for (Ability a : abilityList) {
            reversedList.add(a);
        }
        return reversedList;
    }

    // Returns a list of 1 to 3 abilities that will be emphasized
    public List<Ability> viewFirstAbilities(Account account) {
        List<Ability> abilities = this.abilityRepository.findByAccount(account);
        List<Ability> reversedAbilities = reverseListOrder(abilities);
        List<Ability> firstabilities = new ArrayList<>();
        if (reversedAbilities.size() > 0) {
            for (int i = 0; i < reversedAbilities.size(); i++) {
                if (i == 3) {
                    break;
                }
                firstabilities.add(reversedAbilities.get(i));
            }
            return firstabilities;
        }
        return firstabilities;
    }

    // Returns a list of 1 or more abilities that will not be emphasized
    public List<Ability> viewLastAbilities(Account account) {
        List<Ability> abilities = this.abilityRepository.findByAccount(account);
        List<Ability> reversedAbilities = reverseListOrder(abilities);
        List<Ability> lastabilities = new ArrayList<>();
        if (reversedAbilities.size() > 3) {
            for (int i = 3; i < reversedAbilities.size(); i++) {
                lastabilities.add(reversedAbilities.get(i));
            }
            return lastabilities;
        }
        return lastabilities;
    }
}
