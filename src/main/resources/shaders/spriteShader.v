#version 330 core
layout(location = 0)in vec2 aPosition;
layout(location = 1)in vec2 aTexCoord;

uniform mat4 _viewProjection;
uniform mat4 _model;

out vec2 texCoord0;

void main() {
	gl_Position = _viewProjection * _model * vec4(aPosition, 0, 1);
	texCoord0 = aTexCoord;
}