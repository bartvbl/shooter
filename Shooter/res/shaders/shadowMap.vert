#version 120
//OUTPUTS
uniform mat4x4 LightMatrixValue;
uniform mat4x4 ViewMatrixValue;

varying vec4 shadowMapPosition;
varying vec3 normal;
varying vec3 worldPos;
varying vec3 lightDirection;
varying vec3 eyeVec;

//VERTEXPROGRAM
void main(void)
{
	//we recover the position in the slice camera space.
	//vec4 modelPos = gl_ModelViewMatrix * gl_Vertex;
	//worldPos=modelPos.xyz/modelPos.w;

	vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);
	lightDirection = vec3(gl_LightSource[0].position.xyz - vVertex);
	eyeVec = -vVertex;
	
	//calculating lighting related vectors
	normal = gl_NormalMatrix * gl_Normal;
	
	//storing texture coordinates
	gl_TexCoord[0] = gl_MultiTexCoord0;
	
	//and we get the xy position. This allows the lookup in the local case
	//neat trick to get the -1, 1 range into 0,1 as is needed for the lookup in the texture
	vec4 lightPos = LightMatrixValue * gl_Vertex;
	shadowMapPosition = 0.5 * (lightPos.xyzw +lightPos.wwww);
	
	//doing required transformations
	gl_Position=ftransform();
}

