package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.parser.*;

public class RuntimeFunc extends RuntimeValue {
    ArrayList<RuntimeValue> params = new ArrayList<>();
    AspFuncDef def;
    RuntimeScope defScope;
    String name;

    public RuntimeFunc(RuntimeValue rv, ArrayList<RuntimeValue> params, RuntimeScope curScope, AspFuncDef def) {
        name = def.toString();
        this.params = params;
        defScope = curScope;
        this.def = def;
    }

    public RuntimeFunc(String name) {
        this.name = name;
    }

    @Override
    public String typeName(){
        return "function";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actPars, AspSyntax where) {
        if (actPars.size() == params.size()) {
            RuntimeScope callScope = new RuntimeScope(defScope);
            RuntimeValue callValue;

            for (int i = 0; i < actPars.size(); i++) {
                callValue = actPars.get(i);

                if (callValue != null) {
                    callScope.assign(params.get(i).getStringValue("parameter", where), callValue);
                }

                // Hva sender man inn her?
                else {
                    callScope.assign(params.get(i).getStringValue("parameter", where), params.get(i));
                }
            }

            try {
                def.getSuite().eval(callScope);
            }
    
            catch (RuntimeReturnValue rrv) {
                return rrv.value;
            }
        }

        else {
            Main.error("Actual and formal parameters do not match.");
        }

        return new RuntimeNoneValue();
    }
}
