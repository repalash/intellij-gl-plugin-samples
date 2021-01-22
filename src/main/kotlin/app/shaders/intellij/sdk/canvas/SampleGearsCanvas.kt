package app.shaders.intellij.sdk.canvas

import org.lwjgl.demo.opengl.GLXGears
import org.lwjgl.opengl.GL32
import org.lwjgl.opengl.awt.GLData

class SampleGearsCanvas(data: GLData?) : IntelliGLCanvas(data) {
    private lateinit var gears: GLXGears

    override fun initGL() {
        super.initGL()
        // Set the clear color
        GL32.glClearColor(0.3f, 0.4f, 0.5f, 1f)
        gears = GLXGears()
    }

    override fun paintGL() {
        gears.setSize(width, height)
        gears.animate()
        gears.render()
        super.paintGL()
    }

    override fun disposeItems() {
        GL32.glUseProgram(0)
//        gears.dispose() // TODO
    }
}