import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.agrachev.build.logic.convention.plugins.getPluginId

@Suppress("unused")
class FeatureDefinitionConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) =
        with(target) {
            pluginManager.apply(getPluginId("conventions-kotlin-library"))
            dependencies {
                add("api", project(":core-navigation"))
            }
        }
}
