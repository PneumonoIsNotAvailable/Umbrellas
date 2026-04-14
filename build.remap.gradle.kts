plugins {
	id("fabric-loom") version "1.15-SNAPSHOT"
	id("maven-publish")
	id("me.modmuss50.mod-publish-plugin") version "1.1.0"
	id("dev.kikugie.fletching-table") version "0.1.0-alpha.22"
}

val javaVersion = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5"))
	JavaVersion.VERSION_21 else JavaVersion.VERSION_17
java.targetCompatibility = javaVersion
java.sourceCompatibility = javaVersion

val awFile = if (stonecutter.eval(stonecutter.current.version, ">=1.21.11"))
	"1.21.11.accesswidener"
else if (stonecutter.eval(stonecutter.current.version, ">=1.21.6"))
	"1.21.6.accesswidener"
else
	"1.21.accesswidener"

base.archivesName.set(project.property("mod_id") as String)
version = "${project.property("mod_version")}+${stonecutter.current.project}+${property("mod_subversion")}"

repositories {
	// Mod Menu
	maven("https://maven.terraformersmc.com/")

	// Core
	exclusiveContent {
		forRepository {
			maven("https://api.modrinth.com/maven")
		}
		filter {
			includeGroup("maven.modrinth")
		}
	}
}

loom {
	accessWidenerPath = rootProject.file("src/main/resources/accesswideners/$awFile")

    splitEnvironmentSourceSets()

	mods {
		create("umbrellas") {
			sourceSet(sourceSets["main"])
			sourceSet(sourceSets["client"])
		}
	}

	runConfigs.all {
		ideConfigGenerated(true)
	}
}

fabricApi {
	configureDataGeneration {
		client = true
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${stonecutter.current.version}")
	mappings(loom.officialMojangMappings())
	modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

	// Fabric API
	modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

	// Core mod
	modImplementation("maven.modrinth:pneumono_core:${property("core_version")}")

	// Mod Menu
	modRuntimeOnly("com.terraformersmc:modmenu:${property("modmenu_version")}")
}

tasks {
	val java = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17

	processResources {
		inputs.property("version", project.property("mod_version"))
		inputs.property("supported_versions", ">=${project.property("min_supported_version")} <=${project.property("max_supported_version")}")

		filesMatching("fabric.mod.json") {
			expand(
				mutableMapOf(
					"version" to project.property("mod_version"),
					"supported_versions" to ">=${project.property("min_supported_version")} <=${project.property("max_supported_version")}",
					"aw_file" to awFile,
					"java" to "$java"
				)
			)
		}
	}

	withType<JavaCompile> {
		options.release.set(java)
	}

	java {
		withSourcesJar()
	}

	jar {
		from("LICENSE") {
			rename {"${it}_${base.archivesName.get()}"}
		}
	}
}

stonecutter {
	replacements.string(current.parsed >= "26.1", "render_replacements") {
		replace("GuiGraphics", "GuiGraphicsExtractor")
	}

	replacements.string(current.parsed >= "26.1", "datagen_replacements") {
		replace("FabricDataOutput", "FabricPackOutput")
		replace("FabricTagProvider", "FabricTagsProvider")
	}

	replacements.string(current.parsed >= "1.21.11", "identifier_replacements") {
		replace("ResourceLocation", "Identifier")
	}

	replacements.string(current.parsed >= "1.21", "bootstrap_replacements") {
		replace("BootstapContext", "BootstrapContext")
	}

	replacements.string(current.parsed >= "1.21.11") {
		replace("critereon", "criterion")
	}

	replacements.string(current.parsed >= "26.1") {
		replace("RenderTypes::entityNoOutline", "RenderTypes::bannerPattern")
	}

	replacements.string(current.parsed >= "1.21.11") {
		replace("RenderType::entitySolid", "RenderTypes::entitySolid")
		replace("RenderType::entityNoOutline", "RenderTypes::entityNoOutline")
		replace("RenderType.entityGlint", "RenderTypes.entityGlint")
	}
}

fletchingTable {
	j52j.register("main") {
		extension("json", "umbrellas.mixins.json5")
		extension("json", "umbrellas.client.mixins.json5")
		extension("json", "assets/umbrellas/models/item/template_umbrella.json5")
	}
}

publishMods {
	file = tasks.remapJar.get().archiveFile
	additionalFiles.from(tasks.remapSourcesJar.get().archiveFile)
	displayName = "Umbrellas ${project.version}"
	version = "${project.version}"
	changelog = rootProject.file("CHANGELOG.md").readText()
	type = STABLE
	modLoaders.addAll("fabric", "quilt")

	val modrinthToken = providers.environmentVariable("MODRINTH_TOKEN")

	dryRun = modrinthToken.getOrNull() == null

	modrinth {
		accessToken = modrinthToken
		projectId = "dgTb67Ox"

		minecraftVersionRange {
			start = "${property("min_supported_version")}"
			end = "${property("max_supported_version")}"
		}

		requires {
			// PneumonoCore
			id = "ZLKQjA7t"
		}

		requires {
			// Fabric API
			id = "P7dR8mSH"
		}
	}
}

// configure the maven publication
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}