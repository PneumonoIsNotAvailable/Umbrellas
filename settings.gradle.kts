pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		mavenCentral()
		gradlePluginPortal()
		maven("https://maven.kikugie.dev/snapshots")
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.8.2"
}

stonecutter {
	create(rootProject) {
		versions("1.21.6")
		vcsVersion = "1.21.6"
	}
}