import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import ru.agrachev.build.logic.convention.plugins.configureAndroidKotlinExtension
import ru.agrachev.build.logic.convention.plugins.configureKotlinExtension
import ru.agrachev.build.logic.convention.plugins.isAndroidProject
import ru.agrachev.build.logic.convention.plugins.libs

@Suppress("unused")
class RemoteRepoConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) =
        with(target) {
            val freeCompilerArgsConfigurator: KotlinJvmCompilerOptions.() -> Unit = {
                freeCompilerArgs.add("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
            }
            (if (isAndroidProject) ::configureAndroidKotlinExtension else ::configureKotlinExtension)
                .call(freeCompilerArgsConfigurator)
            dependencies {
                add(
                    "implementation",
                    libs.findLibrary("retrofit").get(),
                )
                add(
                    "implementation",
                    libs.findLibrary("moshi-kotlin").get(),
                )
            }
        }
}
