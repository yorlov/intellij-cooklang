package it.orlov.cooklang

import com.intellij.openapi.fileTypes.LanguageFileType
import it.orlov.cooklang.CooklangIcons.FILE

object CooklangFileType : LanguageFileType(CooklangLanguage) {
    override fun getName() = "Cooklang"
    override fun getDescription() = "Cooklang language file"
    override fun getDefaultExtension() = "cook"
    override fun getIcon() = FILE
}