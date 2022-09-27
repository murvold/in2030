package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

// ABSTRACT
public abstract class AspStmt extends AspSyntax{
    public AspStmt(int n){
        super(n);
    }

    static AspStmt parse(Scanner s){
        enterParser("stmt");

        AspStmt as = null;

        switch (s.curToken().kind){
            case forToken:
                as = AspCompoundStmt.parse(s); break;
            case ifToken:
                as = AspCompoundStmt.parse(s); break;
            case whileToken:
                as = AspCompoundStmt.parse(s); break;
            case defToken:
                as = AspCompoundStmt.parse(s); break;
            default:
                as = AspSmallStmtList.parse(s); break;
        }

        leaveParser("stmt");
        return as;
    }
}
