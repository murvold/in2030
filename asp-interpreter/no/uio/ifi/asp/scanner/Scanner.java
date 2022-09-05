// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
	curFileName = fileName;
	indents.push(0);

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));
	} catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
    }


    private void scannerError(String message) {
	String m = "Asp scanner error";
	if (curLineNum() > 0)
	    m += " on line " + curLineNum();
	m += ": " + message;

	Main.error(m);
    }


    public Token curToken() {
	while (curLineTokens.isEmpty()) {
	    readNextLine();
	}
	return curLineTokens.get(0);
    }


    public void readNextToken() {
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }


    private void readNextLine() {
	curLineTokens.clear();

	// Read the next line:
	String line = null;
	try {
	    line = sourceFile.readLine();
	    if (line == null) {
		sourceFile.close();
		sourceFile = null;
	    } else {
		Main.log.noteSourceLine(curLineNum(), line);
	    }
	} catch (IOException e) {
	    sourceFile = null;
	    scannerError("Unspecified I/O error!");
	}

	//-- Must be changed in part 1:

	// Terminate line:
	curLineTokens.add(new Token(newLineToken,curLineNum()));

	for (Token t: curLineTokens) 
	    Main.log.noteToken(t);
    }

    public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent < s.length() && s.charAt(indent) == ' ') indent++;
		return indent;
    }

    private String expandLeadingTabs(String s) {
		int n = 0;
		int m = 0;
		boolean tabOrSpace = true;
		String tmp = "";

		while (tabOrSpace) {
			m++;
			if (s.charAt(n) == ' ') n++;
			else if (s.charAt(n) == '	') {
				n += 4 - (n % 4);
			}

			else {
				tabOrSpace = false;
			}
		}

		//legger til riktig antall spaces og resten av stringen
		for(int i = 0; i < n; i++){
			tmp += ' ';
		}
		tmp += s.substring(m);
		
		return tmp;
    }


	private void createIndents(String s){
		String tmp;
		int indentCount = 0;
		//sjekker om linje bare inneholder blanke 
		for(int i = 0;  i < s.length(); i++){
			if(s.charAt(i) != ' '){
				return;
			}
		}

		//omformer TAB-er til blanke 
		tmp = expandLeadingTabs(s);

		//teller innledende blanke 
		indentCount = findIndent(tmp);

		//pusher og popper fra stack
		if (indentCount > indents.peek()) {
			indents.push(indentCount);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		}

		else {
			indents.pop();
			curLineTokens.add(new Token(dedentToken, curLineNum()));
		}

		if (indentCount != indents.peek()){
			// Indenteringsfeil.
			System.out.println("Indenteringsfeil.");
		}
	}


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


    public boolean isFactorPrefix() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


    public boolean isFactorOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }
	

    public boolean isTermOpr() {
	TokenKind k = curToken().kind;
	//-- Must be changed in part 2:
	return false;
    }


    public boolean anyEqualToken() {
	for (Token t: curLineTokens) {
	    if (t.kind == equalToken) return true;
	    if (t.kind == semicolonToken) return false;
	}
	return false;
    }
}
