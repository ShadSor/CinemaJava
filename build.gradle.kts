plugins {
    java
    application
}

repositories { mavenCentral() }

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application { mainClass.set("ru.cinema.Main") }

tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

tasks.test { useJUnitPlatform() }

tasks.named<JavaExec>("run") { standardInput = System.`in` }
