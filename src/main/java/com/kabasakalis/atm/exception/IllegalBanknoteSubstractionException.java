package com.kabasakalis.atm.exception;

import com.kabasakalis.atm.Banknote;
import com.kabasakalis.atm.BanknoteBundle;

public class IllegalBanknoteSubstractionException extends BaseAtmException {
    private  BanknoteBundle leftOperand;
    private  BanknoteBundle rightOperand;
 public IllegalBanknoteSubstractionException(BanknoteBundle leftOperand, BanknoteBundle rightOperand) {
    super();

    this.leftOperand = leftOperand;
     this.rightOperand = rightOperand;
  }
  @Override
    public String getMessage() {
     Banknote banknote =  leftOperand.getBanknotes().stream().findFirst().get();
     String banknoteSymbol = banknote.getSymbol() ;
     String rightOperandNoteCount = rightOperand.getBanknoteCount(banknote).toString();
     String leftOperandNoteCount = leftOperand.getBanknoteCount(banknote).toString();
     return  "Could not extract " +
              rightOperandNoteCount + banknoteSymbol + " notes" +
             "from "  + leftOperandNoteCount + banknoteSymbol + "notes";
    }


}





