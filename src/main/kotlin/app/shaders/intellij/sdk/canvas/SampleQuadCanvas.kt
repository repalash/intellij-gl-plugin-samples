package app.shaders.intellij.sdk.canvas

import org.lwjgl.opengl.GL32.*
import org.lwjgl.opengl.awt.GLData

class SampleQuadCanvas(data: GLData?) : IntelliGLCanvas(data) {
    private var aspectUniform: Int = 0

    override fun initGL() {
        super.initGL()
        // Set the clear color
        println("OpenGL version: " + effective.majorVersion + "." + effective.minorVersion + " (Profile: " + effective.profile + ")")
        glClearColor(0.3f, 0.4f, 0.5f, 1f)
        glBindVertexArray(glGenVertexArrays())
        val vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(
            GL_ARRAY_BUFFER,
            floatArrayOf(-0.5f, 0f, 0f, -0.5f, 0.5f, 0f, 0.5f, 0f, 0f, 0.5f, -0.5f, 0f),
            GL_STATIC_DRAW
        )
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0L)
        glEnableVertexAttribArray(0)
        val vs = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(
            vs,
            "#version 150 core\nuniform float aspect;in vec2 vertex;void main(void){gl_Position=vec4(vertex/vec2(aspect, 1.0), 0.0, 1.0);}"
        )
        glCompileShader(vs)
        if (glGetShaderi(vs, GL_COMPILE_STATUS) == 0)
            throw AssertionError("Could not compile vertex shader: " + glGetShaderInfoLog(vs))
        val fs = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fs, "#version 150 core\nout vec4 color;void main(void){color=vec4(0.4, 0.6, 0.8, 1.0);}")
        glCompileShader(fs)
        if (glGetShaderi(fs, GL_COMPILE_STATUS) == 0)
            throw AssertionError("Could not compile fragment shader: " + glGetShaderInfoLog(fs))
        val prog = glCreateProgram()
        glAttachShader(prog, vs)
        glAttachShader(prog, fs)
        glLinkProgram(prog)
        if (glGetProgrami(prog, GL_LINK_STATUS) == 0)
            throw AssertionError("Could not link program: " + glGetProgramInfoLog(prog))
        glUseProgram(prog)
        aspectUniform = glGetUniformLocation(prog, "aspect")
    }

    override fun paintGL() {
        val aspect: Float = width.toFloat() / height
        glClear(GL_COLOR_BUFFER_BIT)
        glViewport(0, 0, width, height)
        glUniform1f(aspectUniform, aspect)
        glDrawArrays(GL_TRIANGLES, 0, 6)
        super.paintGL()
    }

    override fun disposeItems() {
        glUseProgram(0)
//            glDeleteShader(vertexShader)
//            glDeleteProgram(currentProgram)
//            glDeleteBuffers(currentVbo)
//            glDeleteVertexArrays(currentVao)
    }
}