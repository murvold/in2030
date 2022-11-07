package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspPassStmt extends AspSmallStmt {
    AspPassStmt(int n) {
        super(n);
    }

    static AspPassStmt parse(Scanner s) {
        enterParser("pass stmt");

        AspPassStmt statement = new AspPassStmt(s.curLineNum());
        skip(s, passToken);

        leaveParser("pass stmt");
        return statement;
    }

    @Override
    public void prettyPrint() {
	    prettyWrite("pass ");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        trace("pass");
        return null;
    }
}

