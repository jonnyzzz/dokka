package org.jetbrains.dokka.Formats

import org.jetbrains.dokka.FormatService
import org.jetbrains.dokka.Generator
import org.jetbrains.dokka.OutlineFormatService

public interface FormatDescriptor {
    val formatServiceClass: Class<out FormatService>?
    val outlineServiceClass: Class<out OutlineFormatService>?
    val generatorServiceClass: Class<out Generator>
}