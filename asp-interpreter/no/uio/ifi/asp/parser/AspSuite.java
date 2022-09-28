package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspSuite extends AspSyntax {
    ArrayList<AspStmt> stmts = new ArrayList<>();
    AspSmallStmtList smallStmtList;

    AspSuite(int n) {
	    super(n);
    }

    public static AspSuite parse(Scanner s) {
        enterParser("suite");

        AspSuite as = new AspSuite(s.curLineNum());

        if(s.curToken().kind == newLineToken){
            skip(s, newLineToken);
            skip(s, indentToken);

            while(true){
                as.stmts.add(AspStmt.parse(s));
                if(s.curToken().kind == dedentToken) break;
            }
            skip(s, dedentToken);
        }else{
            as.smallStmtList = AspSmallStmtList.parse(s);
        }

        leaveParser("suite");
        return as;
    }

    @Override
    public void prettyPrint() {
	    //-- Must be changed in part 2:
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}