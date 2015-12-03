package org.jetbrains.dokka

import com.google.inject.Inject
import com.google.inject.name.Named
import java.io.File

public fun FoldersLocationService(root: String): FoldersLocationService = FoldersLocationService(File(root), "")
public class FoldersLocationService @Inject constructor(@Named("outputDir") val rootFile: File, val extension: String) : FileLocationService {
    override val root: Location
        get() = FileLocation(rootFile)

    override fun withExtension(newExtension: String): FileLocationService {
        return if (extension.isEmpty()) FoldersLocationService(rootFile, newExtension) else this
    }

    override fun location(qualifiedName: List<String>, hasMembers: Boolean): FileLocation {
        return FileLocation(File(rootFile, relativePathToNode(qualifiedName, hasMembers)).appendExtension(extension))
    }
}

fun relativePathToNode(qualifiedName: List<String>, hasMembers: Boolean): String {
    val parts = qualifiedName.map { identifierToFilename(it) }.filterNot { it.isEmpty() }
    return if (!hasMembers) {
        // leaf node, use file in owner's folder
        parts.joinToString("/")
    } else {
        parts.joinToString("/") + (if (parts.none()) "" else "/") + "index"
    }
}