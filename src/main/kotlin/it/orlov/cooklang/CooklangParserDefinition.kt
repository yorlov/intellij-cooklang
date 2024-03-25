package it.orlov.cooklang

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import it.orlov.cooklang.psi.CooklangParser
import it.orlov.cooklang.psi.CooklangTypes

class CooklangParserDefinition : ParserDefinition {
  override fun createLexer(project: Project) = CooklangLexerAdapter()
  override fun createParser(project: Project): PsiParser = CooklangParser()
  override fun getCommentTokens(): TokenSet = CooklangTokenSets.COMMENTS
  override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY
  override fun getFileNodeType() = FILE
  override fun createFile(viewProvider: FileViewProvider): PsiFile = CooklangPsiFile(viewProvider)
  override fun createElement(node: ASTNode): PsiElement = CooklangTypes.Factory.createElement(node)

  private val FILE = IFileElementType(CooklangLanguage)
}

private object CooklangTokenSets {
  val COMMENTS: TokenSet = TokenSet.create(CooklangTypes.COMMENT)
}