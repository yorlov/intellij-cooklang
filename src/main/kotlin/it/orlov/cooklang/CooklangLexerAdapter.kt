package it.orlov.cooklang

import com.intellij.lexer.FlexAdapter
import it.orlov.cooklang.lexer._CooklangLexer

class CooklangLexerAdapter : FlexAdapter(_CooklangLexer(null))