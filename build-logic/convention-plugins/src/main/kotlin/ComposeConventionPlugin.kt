import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.agrachev.build.logic.convention.plugins.commonExtension
import ru.agrachev.build.logic.convention.plugins.getPluginId
import ru.agrachev.build.logic.convention.plugins.libs

@Suppress("unused")
class ComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) =
        with(target) {
            pluginManager.apply(getPluginId("kotlin-compose"))
            commonExtension.run {
                buildFeatures.apply {
                    compose = true
                }
            }
            dependencies {
                add(
                    "implementation",
                    libs.findLibrary("androidx-lifecycle-runtime-ktx").get(),
                )
                add(
                    "implementation",
                    platform(
                        libs.findLibrary("androidx-compose-bom").get(),
                    ),
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-compose-ui").get(),
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-compose-ui-graphics").get(),
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-compose-ui-tooling-preview").get(),
                )
                add(
                    "implementation",
                    libs.findLibrary("androidx-compose-material3").get(),
                )
                add(
                    "debugImplementation",
                    libs.findLibrary("androidx-compose-ui-tooling").get(),
                )
                add(
                    "debugImplementation",
                    libs.findLibrary("androidx-compose-ui-test-manifest").get(),
                )
                add(
                    "androidTestImplementation",
                    platform(
                        libs.findLibrary("androidx-compose-bom").get(),
                    ),
                )
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-compose-ui-test-junit4").get(),
                )
            }
        }
}
