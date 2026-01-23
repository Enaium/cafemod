package cn.enaium.cefemod

import cn.enaium.cafemod.Cafemod
import cn.enaium.cafemod.impl.*
import cn.enaium.cafemod.model.ZipEntry
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.runBlocking
import org.objectweb.asm.Opcodes
import kotlin.io.path.Path
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 * @author Enaium
 */
class CafemodTest {

    lateinit var cafemod: Cafemod

    @BeforeTest
    fun before() {
        cafemod = Cafemod(
            Path(System.getProperty("user.home"))
                .resolve(".gradle/caches/modules-2/files-2.1/org.ow2.asm/asm/9.9.1/2ceea6ab43bcae1979b2a6d85fc0ca429877e5ab/asm-9.9.1.jar")
                .toFile().absolutePath
        )
        runBlocking {
            cafemod.load()
        }
    }

    @Test
    fun tree() {
        assertContentEquals(
            cafemod.findEntry("")?.children?.map { it.name },
            listOf("META-INF", "module-info.class", "org")
        )
        assertContentEquals(cafemod.findEntry("META-INF")?.children?.map { it.name }, listOf("MANIFEST.MF"))
        assertContentEquals(cafemod.findEntry("org")?.children?.map { it.name }, listOf("objectweb"))
        assertContentEquals(cafemod.findEntry("org/objectweb")?.children?.map { it.name }, listOf("asm"))
        assertContentEquals(
            cafemod.findEntry("org/objectweb/asm/signature")?.children?.map { it.name },
            listOf("SignatureReader.class", "SignatureVisitor.class", "SignatureWriter.class")
        )
        assertEquals(cafemod.findEntry("org/objectweb/asm/signature/SignatureReader.class")?.let {
            val names = mutableListOf<String>()
            var parent: ZipEntry? = it.parent

            while (parent != null) {
                if (parent.name.isNotBlank()) {
                    names.add(parent.name)
                }
                parent = parent.parent
            }

            names.reversed().joinToString("/")
        }, "org/objectweb/asm/signature")
    }

    @Test
    fun content() {
        assertEquals(
            cafemod.readContent("META-INF/MANIFEST.MF").use { it?.reader()?.readText() }, "Manifest-Version: 1.0\r\n" +
                    "Implementation-Title: ASM, a very small and fast Java bytecode manipulat\r\n" +
                    " ion framework\r\n" +
                    "Implementation-Version: 9.9.1\r\n" +
                    "Bundle-DocURL: http://asm.ow2.org\r\n" +
                    "Bundle-License: BSD-3-Clause;link=https://asm.ow2.io/LICENSE.txt\r\n" +
                    "Bundle-ManifestVersion: 2\r\n" +
                    "Bundle-Name: org.objectweb.asm\r\n" +
                    "Bundle-RequiredExecutionEnvironment: J2SE-1.5\r\n" +
                    "Bundle-SymbolicName: org.objectweb.asm\r\n" +
                    "Bundle-Version: 9.9.1\r\n" +
                    "Export-Package: org.objectweb.asm;version=\"9.9.1\",org.objectweb.asm.sign\r\n" +
                    " ature;version=\"9.9.1\"\r\n" +
                    "\r\n"
        )
    }

    @Test
    fun trace() {
        assertEquals(
            cafemod.getTracePrinted("org/objectweb/asm/Context.class"), "// class version 49.0 (49)\n" +
                    "// access flags 0x30\n" +
                    "final class org/objectweb/asm/Context {\n" +
                    "\n" +
                    "  // compiled from: Context.java\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [Lorg/objectweb/asm/Attribute; attributePrototypes\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I parsingOptions\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [C charBuffer\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I currentMethodAccessFlags\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  Ljava/lang/String; currentMethodName\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  Ljava/lang/String; currentMethodDescriptor\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [Lorg/objectweb/asm/Label; currentMethodLabels\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I currentTypeAnnotationTarget\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  Lorg/objectweb/asm/TypePath; currentTypeAnnotationTargetPath\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [Lorg/objectweb/asm/Label; currentLocalVariableAnnotationRangeStarts\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [Lorg/objectweb/asm/Label; currentLocalVariableAnnotationRangeEnds\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [I currentLocalVariableAnnotationRangeIndices\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I currentFrameOffset\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I currentFrameType\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I currentFrameLocalCount\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I currentFrameLocalCountDelta\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [Ljava/lang/Object; currentFrameLocalTypes\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  I currentFrameStackCount\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  [Ljava/lang/Object; currentFrameStackTypes\n" +
                    "\n" +
                    "  // access flags 0x0\n" +
                    "  <init>()V\n" +
                    "   L0\n" +
                    "    LINENUMBER 36 L0\n" +
                    "    ALOAD 0\n" +
                    "    INVOKESPECIAL java/lang/Object.<init> ()V\n" +
                    "    RETURN\n" +
                    "   L1\n" +
                    "    LOCALVARIABLE this Lorg/objectweb/asm/Context; L0 L1 0\n" +
                    "    MAXSTACK = 1\n" +
                    "    MAXLOCALS = 1\n" +
                    "}\n"
        )
    }

    @Test
    fun methodNameDescriptors() {
        assertContentEquals(
            cafemod.getMemberNds("org/objectweb/asm/signature/SignatureReader.class"),
            listOf(
                "signatureValue:Ljava/lang/String;",
                "<init>:(Ljava/lang/String;)V",
                "accept:(Lorg/objectweb/asm/signature/SignatureVisitor;)V",
                "acceptType:(Lorg/objectweb/asm/signature/SignatureVisitor;)V",
                "parseType:(Ljava/lang/String;ILorg/objectweb/asm/signature/SignatureVisitor;)I"
            )
        )
        cafemod.getMethod(
            "org/objectweb/asm/signature/SignatureReader.class",
            "accept:(Lorg/objectweb/asm/signature/SignatureVisitor;)V"
        ).also {
            assertEquals(it?.name, "accept")
            assertEquals(it?.desc, "(Lorg/objectweb/asm/signature/SignatureVisitor;)V")
            assertEquals(it?.access, Opcodes.ACC_PUBLIC)
        }
    }

    @Test
    fun methodInstruction() {
        assertEquals(
            jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                cafemod.getMethodInstructions(
                    "org/objectweb/asm/Context.class",
                    "<init>:()V"
                )
            ),
            "{\r\n" +
                    "  \"0\" : {\r\n" +
                    "    \"type\" : 8,\r\n" +
                    "    \"opcode\" : -1,\r\n" +
                    "    \"labelIndex\" : 0\r\n" +
                    "  },\r\n" +
                    "  \"1\" : {\r\n" +
                    "    \"type\" : 15,\r\n" +
                    "    \"opcode\" : -1,\r\n" +
                    "    \"line\" : 36,\r\n" +
                    "    \"startLabelIndex\" : 0\r\n" +
                    "  },\r\n" +
                    "  \"2\" : {\r\n" +
                    "    \"type\" : 2,\r\n" +
                    "    \"opcode\" : 25,\r\n" +
                    "    \"var\" : 0\r\n" +
                    "  },\r\n" +
                    "  \"3\" : {\r\n" +
                    "    \"type\" : 5,\r\n" +
                    "    \"opcode\" : 183,\r\n" +
                    "    \"owner\" : \"java/lang/Object\",\r\n" +
                    "    \"name\" : \"<init>\",\r\n" +
                    "    \"desc\" : \"()V\"\r\n" +
                    "  },\r\n" +
                    "  \"4\" : {\r\n" +
                    "    \"type\" : 0,\r\n" +
                    "    \"opcode\" : 177\r\n" +
                    "  },\r\n" +
                    "  \"5\" : {\r\n" +
                    "    \"type\" : 8,\r\n" +
                    "    \"opcode\" : -1,\r\n" +
                    "    \"labelIndex\" : 0\r\n" +
                    "  }\r\n" +
                    "}"
        )
    }
}