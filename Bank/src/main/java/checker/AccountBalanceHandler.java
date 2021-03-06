package checker;

import exception.NotEnoughMoneyException;
import model.account.Account;
import transaction.Transfer;

public class AccountBalanceHandler extends RequestHandler {
    @Override
    public void handle(Transfer request, Account account) {
        if (account.getBalance() <= request.getAmount() || !account.withdrawalAvailable())
            throw new NotEnoughMoneyException();
        account.get(request.getAmount());
        next(request, account);
    }
}
