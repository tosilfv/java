package projekti.logic.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.domain.Account;
import projekti.logic.utility.CustomDate;

@Service
public class ConnectionsService {

    @Autowired
    private CustomDate date;

    // Add visited user alias and name to the connectionsEstablished and establishedUseraliases lists list of user account if it's not there yet
    @Transactional
    public void acceptRequest(Account userAccount, Account visitingAccount) {
        for (String[] s : userAccount.getConnectionsEstablished()) {
            if (s[0].equals(visitingAccount.getUseralias())) {
                return;
            }
        }
        String visitingUseralias = visitingAccount.getUseralias();
        String[] userInfo = {visitingUseralias, visitingAccount.getRealname()};
        userAccount.getConnectionsEstablished().add(userInfo);
        userAccount.getEstablishedUseraliases().add(visitingUseralias);
    }

    // Arrange the connectionsEstablished list of the parameter account by real name in alphabetical order
    @Transactional
    public void arrangeConnectionsEstablished(Account account) {
        Collections.sort(account.getConnectionsEstablished(), (a, b) -> a[1].compareToIgnoreCase(b[1]));
    }

    // Remove user alias and name from the connectionRequestsReceived list of visited user account
    @Transactional
    public void cancelRequestReceived(Account userAccount, Account visitingAccount) {
        for (String[] s : visitingAccount.getConnectionRequestsReceived()) {
            if (s[0].equals(userAccount.getUseralias())) {
                visitingAccount.getConnectionRequestsReceived().remove(s);
                break;
            }
        }
    }

    // Remove user alias and name from the connectionRequestsSent list of visited user account
    @Transactional
    public void cancelRequestSent(Account userAccount, Account visitingAccount) {
        for (String[] s : visitingAccount.getConnectionRequestsSent()) {
            if (s[0].equals(userAccount.getUseralias())) {
                visitingAccount.getConnectionRequestsSent().remove(s);
                break;
            }
        }
    }

    // Check if connectionsEstablished list of user account contains visited user alias
    public boolean connectionIsEstablished(Account userAccount, Account visitingAccount) {
        for (String[] s : userAccount.getConnectionsEstablished()) {
            if (s[0].equals(visitingAccount.getUseralias())) {
                return true;
            }
        }
        return false;
    }

    // 1. Remove visited user alias and name from the connectionsEstablished list of user account
    // 2. Remove user alias and name from the connectionsEstablished list of visited user account
    @Transactional
    public void connectionRemove(Account userAccount, Account visitingAccount) {
        // 1.
        removeConnectionUser(userAccount, visitingAccount);
        // 2.
        removeConnectionVisited(userAccount, visitingAccount);
    }

    // 1. Remove visited user alias and name from the connectionRequestsReceived list of user account
    // 2. Remove user alias and name from the connectionRequestsSent list of visited user account
    // 3. Add visited user alias and name to the connectionsEstablished list of user account if it's not there yet
    // 4. Add user alias and name to the connectionsEstablished list of visited user account if it's not there yet
    @Transactional
    public void connectionRequestAccept(Account userAccount, Account visitingAccount) {
        // 1.
        connectionRequestReject(userAccount, visitingAccount);
        // 2.
        cancelRequestSent(userAccount, visitingAccount);
        // 3.
        acceptRequest(userAccount, visitingAccount);
        // 4.
        requestAccepted(userAccount, visitingAccount);
    }

    // Remove visited user alias and name from the connectionRequestsSent list of user account
    @Transactional
    public void connectionRequestCancel(Account userAccount, Account visitingAccount) {
        for (String[] s : userAccount.getConnectionRequestsSent()) {
            if (s[0].equals(visitingAccount.getUseralias())) {
                userAccount.getConnectionRequestsSent().remove(s);
                break;
            }
        }
        cancelRequestReceived(userAccount, visitingAccount);
    }

    // Add user alias, real name and request receiving time to the connectionRequestsReceived list of visited user account if it's not there yet
    @Transactional
    public void connectionRequestReceived(Account userAccount, Account visitingAccount) {
        for (String[] s : visitingAccount.getConnectionRequestsReceived()) {
            if (s[0].equals(userAccount.getUseralias())) {
                return;
            }
        }
        String[] userInfo = {userAccount.getUseralias(), userAccount.getRealname(), this.date.dateTime()};
        visitingAccount.getConnectionRequestsReceived().add(userInfo);
    }

    // Remove visited user alias and name from the connectionRequestsReceived list of user account
    @Transactional
    public void connectionRequestReject(Account userAccount, Account visitingAccount) {
        for (String[] s : userAccount.getConnectionRequestsReceived()) {
            if (s[0].equals(visitingAccount.getUseralias())) {
                userAccount.getConnectionRequestsReceived().remove(s);
                break;
            }
        }
        cancelRequestSent(userAccount, visitingAccount);
    }

    // Add visited user alias, real name and request sending time to the connectionRequestsSent list of user account if it's not there yet
    @Transactional
    public boolean connectionRequestSent(Account userAccount, Account visitingAccount) {
        if (requestIsSent(userAccount, visitingAccount) == true) {
            return true;
        }
        String[] userInfo = {visitingAccount.getUseralias(), visitingAccount.getRealname(), this.date.dateTime()};
        userAccount.getConnectionRequestsSent().add(userInfo);
        return false;
    }

    // Remove visited user alias and name from the connectionsEstablished and establishedUseraliases lists of user account
    @Transactional
    public void removeConnectionUser(Account userAccount, Account visitingAccount) {
        for (String[] s : userAccount.getConnectionsEstablished()) {
            if (s[0].equals(visitingAccount.getUseralias())) {
                userAccount.getConnectionsEstablished().remove(s);
                userAccount.getEstablishedUseraliases().remove(visitingAccount.getUseralias());
                break;
            }
        }
    }

    // Remove user alias and name from the connectionsEstablished and establishedUseraliases lists of visited user account
    @Transactional
    public void removeConnectionVisited(Account userAccount, Account visitingAccount) {
        for (String[] s : visitingAccount.getConnectionsEstablished()) {
            if (s[0].equals(userAccount.getUseralias())) {
                visitingAccount.getConnectionsEstablished().remove(s);
                visitingAccount.getEstablishedUseraliases().remove(userAccount.getUseralias());
                break;
            }
        }
    }

    // Add user alias and name to the connectionsEstablished and establishedUseraliases lists of visited user account if it's not there yet
    @Transactional
    public void requestAccepted(Account userAccount, Account visitingAccount) {
        for (String[] s : visitingAccount.getConnectionsEstablished()) {
            if (s[0].equals(userAccount.getUseralias())) {
                return;
            }
        }
        String useralias = userAccount.getUseralias();
        String[] userInfo = {useralias, userAccount.getRealname()};
        visitingAccount.getConnectionsEstablished().add(userInfo);
        visitingAccount.getEstablishedUseraliases().add(useralias);
    }

    // Check if connectionRequestsReceived list of user account contains visited user alias
    public boolean requestIsReceived(Account userAccount, Account visitingAccount) {
        for (String[] s : userAccount.getConnectionRequestsReceived()) {
            if (s[0].equals(visitingAccount.getUseralias())) {
                return true;
            }
        }
        return false;
    }

    // Check if connectionRequestsSent list of user account contains visited user alias
    public boolean requestIsSent(Account userAccount, Account visitingAccount) {
        for (String[] s : userAccount.getConnectionRequestsSent()) {
            if (s[0].equals(visitingAccount.getUseralias())) {
                return true;
            }
        }
        return false;
    }
}
