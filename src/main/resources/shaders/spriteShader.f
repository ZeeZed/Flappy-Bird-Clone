#version 330 core
layout(location = 0)out vec4 color;

uniform sampler2D _texture;

in vec2 texCoord0;

void main() {
	color = texture(_texture, texCoord0);
}