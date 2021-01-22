package app.shaders.intellij.sdk.toolWindow

import app.shaders.intellij.sdk.canvas.IntelliGLCanvas
import app.shaders.intellij.sdk.canvas.SampleGearsCanvas
import app.shaders.intellij.sdk.canvas.SampleQuadCanvas
import com.intellij.openapi.wm.ToolWindow
import org.lwjgl.opengl.awt.AWTGLCanvas
import org.lwjgl.opengl.awt.GLData
import java.awt.BorderLayout
import javax.swing.JPanel

open class CanvasToolWindow(val toolWindow: ToolWindow) {

    private val canvas: IntelliGLCanvas
    private lateinit var windowContent: JPanel

    init {
        val glData = GLData()
        glData.majorVersion = 3
        glData.minorVersion = 2
        glData.profile = GLData.Profile.CORE
        glData.samples = 4
        glData.swapInterval = 0
        canvas = when(toolWindow.id){
            "GL Quad" -> SampleQuadCanvas(glData)
            "GL Gears" -> SampleGearsCanvas(glData)
            else -> IntelliGLCanvas(glData)
        }
        windowContent.add(canvas, BorderLayout.CENTER)
        canvas.start()
    }

    val content: JPanel get() = windowContent

}
