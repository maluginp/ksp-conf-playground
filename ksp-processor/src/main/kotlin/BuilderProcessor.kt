import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.OutputStream

fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}

class BuilderProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("ru.maluginp.common.Builder")
        logger.logging("Found ${symbols.toList().size} symbols")
        val ret = symbols.filterNot { it.validate() }.toList()
        logger.logging("${ret.size} invalid symbols")
        symbols
            .filter { it is KSClassDeclaration && it.validate() }
            .forEach { it.accept(BuilderVisitor(resolver), Unit) }
        return ret
    }

    inner class BuilderVisitor(
        private val resolver: Resolver,
    ) : KSVisitorVoid() {

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            classDeclaration.primaryConstructor!!.accept(this, data)
        }

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val parent = function.parentDeclaration as KSClassDeclaration
            val packageName = parent.containingFile!!.packageName.asString()

            val className = parent.simpleName.asString()
            val classNameBuilder = "${className}Builder"
            FileSpec.builder(packageName, classNameBuilder)
                .addType(
                    TypeSpec.classBuilder(classNameBuilder).apply {

                        function.parameters.forEach {
                            val varType = it.type.toTypeName()
                            val varName = it.name!!.asString()
                            val builderFuncName = "with${varName.replaceFirstChar { c -> c.uppercase() }}"

                            addProperty(
                                PropertySpec.builder(
                                    varName, varType.copy(nullable = true), KModifier.PRIVATE
                                ).mutable()
                                    .initializer("null")
                                    .build()
                            )
                            addFunction(
                                FunSpec.builder(builderFuncName)
                                    .addModifiers(KModifier.INTERNAL)
                                    .addParameter(
                                        varName, varType
                                    )
                                    .addStatement("this.$varName = $varName")
                                    .addStatement("return this")
                                    .returns(ClassName(packageName, classNameBuilder))
                                    .build()
                            )
                        }

                        val parameters = function.parameters
                            .joinToString(", ") {
                                it.name!!.asString()+"!!"
                            }


                        addFunction(
                            FunSpec.builder("build")
                                .addModifiers(KModifier.INTERNAL)
                                .returns(ClassName(packageName, className))
                                .addStatement("return $className($parameters)")
                                .build()

                        )
                    }.build()
                ).build().writeTo(
                    codeGenerator = codeGenerator,
                    aggregating = true,
                )
        }
    }
}

class BuilderProcessorProvider : SymbolProcessorProvider {

    override fun create(
        environment: SymbolProcessorEnvironment,
    ): SymbolProcessor {
        return BuilderProcessor(environment.codeGenerator, environment.logger)
    }
}
