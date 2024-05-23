import io.papermc.sculptor.shared.util.MinecraftJarType

plugins {
    id("io.papermc.sculptor.version") version "1.0.8-SNAPSHOT"
}

val generateReportsProperty = providers.gradleProperty("generateReports")
mache {
    minecraftVersion = "1.20.6"

    repositories.register("sonatype snapshots") {
        url = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
        includeGroups.add("org.vineflower")
    }

    val args = mutableListOf(
        "--temp-dir={tempDir}",
        "--remapper-file={remapperFile}",
        "--mappings-file={mappingsFile}",
        "--params-file={paramsFile}",
        // "--constants-file={constantsFile}",
        "--output={output}",
        "--input={input}",
        "--input-classpath={inputClasspath}",
    )
    if (generateReportsProperty.getOrElse("false").toBooleanStrict()) {
        args.addAll(listOf(
            "--reports-dir={reportsDir}",
            "--all-reports",
        ))
    }
    minecraftJarType = MinecraftJarType.CLIENT

    remapperArgs.set(args)
}

dependencies {
    codebook("1.0.10")
    remapper(art("1.0.14"))
    decompiler(vineflower("1.11.0-20240520.024632-26"))
    parchment("1.20.5", "BLEEDING-SNAPSHOT")
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
}
