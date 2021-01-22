package app.shaders.intellij.sdk.canvas

import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL32.*
import org.lwjgl.opengl.awt.AWTGLCanvas
import org.lwjgl.opengl.awt.GLData
import java.lang.Exception
import javax.swing.SwingUtilities

open class IntelliGLCanvas(data: GLData?) : AWTGLCanvas(data) {

    override fun initGL() {
        GL.createCapabilities();
    }

    override fun paintGL() {
        swapBuffers()
        repaint()
    }

    open fun disposeItems(){
        glUseProgram(0)
//            glDeleteShader(vertexShader)
//            glDeleteProgram(currentProgram)
//            glDeleteBuffers(currentVbo)
//            glDeleteVertexArrays(currentVao)
    }

    override fun removeNotify() {
        if (context != 0L && initCalled) {
            try {
                executeInContext {
                    disposeItems();
                    0
                }
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                platformCanvas.deleteContext(context)
            }
        }
        super.removeNotify()
        running = false
    }

    override fun addNotify() {
        super.addNotify()
        if (renderLoop == null) start()
    }

    private var running = false
    private var renderLoop: Runnable? = null
    fun start() {
        if (renderLoop != null) return
        running = true
        renderLoop = object : Runnable {
            override fun run() {
                if (!running || !isValid) {
                    running = false
                    renderLoop = null
                    return
                }
                render()
                SwingUtilities.invokeLater(this)
            }
        }
        SwingUtilities.invokeLater(renderLoop)
    }
}