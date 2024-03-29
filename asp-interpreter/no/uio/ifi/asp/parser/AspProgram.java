package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspProgram extends AspSyntax {
    ArrayList<AspStmt> stmts = new ArrayList<>();

    AspProgram(int n) {
	    super(n);
    }

    public static AspProgram parse(Scanner s) {
        enterParser("program");
        AspProgram ap = new AspProgram(s.curLineNum());

        if(s.curToken().kind == eofToken);
        else{
            while(true){
                if(s.curToken().kind == eofToken) break;
                ap.stmts.add(AspStmt.parse(s));
            }
        }
        skip(s, eofToken);
        

        leaveParser("program");
        return ap;
    }

    @Override
    public void prettyPrint() {
	    for (AspStmt as : stmts){
            as.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = null;

        for (AspStmt stmt : stmts) {
            value = stmt.eval(curScope);
        }

        return value;
    }
}
