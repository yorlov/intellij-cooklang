package it.orlov.cooklang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static it.orlov.cooklang.psi.CooklangTypes.*;

%%

%{
  public _CooklangLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _CooklangLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s

PLAIN_WORD=\p{Alnum}+
PUNCTUATION_CHARACTER=\p{Punct}
NON_WORD_CHARACTER=\W

%state METADATA_KEY, METADATA_VALUE, INGREDIENT, COOKWARE, TIMER, AMOUNT, BLOCK_COMMENT

%%
<YYINITIAL> {
  {EOL}                         { return EOL; }
  {WHITE_SPACE}                 { return WHITE_SPACE; }

  "@"                           { yybegin(INGREDIENT); return AT; }
  "~"                           { yybegin(TIMER); return TILDE; }
  "#"                           { yybegin(COOKWARE); return SIGN; }
  "--"                          { return DOUBLE_MINUS; }
  ">>"                          { yybegin(METADATA_KEY); return DOUBLE_GREATER; }
  "[-"                          { yybegin(BLOCK_COMMENT); return LEFT_SQUARE_BRACKET_MINUS; }

  {PLAIN_WORD}                  { return PLAIN_WORD; }
  {NON_WORD_CHARACTER}          { return NON_WORD_CHARACTER; }
  {PUNCTUATION_CHARACTER}       { return PUNCTUATION_CHARACTER; }
}

<INGREDIENT> {
  "{"                           { yybegin(AMOUNT); return LEFT_BRACE; }

  {WHITE_SPACE}                 { return WHITE_SPACE; }
  {PLAIN_WORD}                  { return PLAIN_WORD; }
  {NON_WORD_CHARACTER}          { yypushback(1); yybegin(YYINITIAL); return NON_WORD_CHARACTER; }
  {PUNCTUATION_CHARACTER}       { return PUNCTUATION_CHARACTER; }
}

<COOKWARE> {
  "{"                           { yybegin(AMOUNT); return LEFT_BRACE; }

  {WHITE_SPACE}                 { return WHITE_SPACE; }
  {PLAIN_WORD}                  { return PLAIN_WORD; }
  {NON_WORD_CHARACTER}          { yypushback(1); yybegin(YYINITIAL); return NON_WORD_CHARACTER; }
  {PUNCTUATION_CHARACTER}       { return PUNCTUATION_CHARACTER; }
}

<TIMER> {
  "{"                           { yybegin(AMOUNT); return LEFT_BRACE; }

  {WHITE_SPACE}                 { return WHITE_SPACE; }
  {PLAIN_WORD}                  { return PLAIN_WORD; }
  {NON_WORD_CHARACTER}          { return NON_WORD_CHARACTER; }
  {PUNCTUATION_CHARACTER}       { return PUNCTUATION_CHARACTER; }
}

<AMOUNT> {
   "}"                           { yybegin(YYINITIAL); return RIGHT_BRACE; }
   "%"                           { return PERCENT; }
   {PLAIN_WORD}                  { return PLAIN_WORD; }
   {NON_WORD_CHARACTER}          { return NON_WORD_CHARACTER; }
   {PUNCTUATION_CHARACTER}       { return PUNCTUATION_CHARACTER; }
}

<METADATA_KEY> {
  ":"                           { yybegin(METADATA_VALUE); return COLON; }
  {WHITE_SPACE}                 { return WHITE_SPACE; }
  {PLAIN_WORD}                  { return PLAIN_WORD; }
  {NON_WORD_CHARACTER}          { return NON_WORD_CHARACTER; }
  {PUNCTUATION_CHARACTER}       { return PUNCTUATION_CHARACTER; }
}

<METADATA_VALUE> {
  {EOL}                                   { yybegin(YYINITIAL); return EOL; }
  {WHITE_SPACE}                           { return WHITE_SPACE; }
  {PLAIN_WORD}                            { return PLAIN_WORD; }
  {NON_WORD_CHARACTER}                    { return NON_WORD_CHARACTER; }
  {PUNCTUATION_CHARACTER}                 { return PUNCTUATION_CHARACTER; }
}

<BLOCK_COMMENT> {
  "-]"                                    { yybegin(YYINITIAL); return MINUS_RIGHT_SQUARE_BRACKET; }
  {WHITE_SPACE}                           { return WHITE_SPACE; }
  {PLAIN_WORD}                            { return PLAIN_WORD; }
  {NON_WORD_CHARACTER}                    { return NON_WORD_CHARACTER; }
  {PUNCTUATION_CHARACTER}                 { return PUNCTUATION_CHARACTER; }
}

[^] { return BAD_CHARACTER; }
