#extension GL_EXT_gpu_shader4 : enable 
#extension GL_ARB_draw_buffers : enable

//INPUTS
//these are standard shadow map coordinates
varying vec4 shadowMapPosition;
varying vec3 normal;
varying vec3 worldPos;
varying vec3 lightDirection;
varying vec3 eyeVector;

//uniforms
uniform sampler2D depthMap;
uniform vec4 LightPosition;
uniform sampler2D texture0;

void main(void)
{
	vec4 textureColour = texture2D(texture0, gl_TexCoord[0].st);
	textureColour += gl_FrontMaterial.ambient;
	
	vec3 normalizedNormal = normalize(normal);
	vec3 normalizedLightDirection = normalize(lightDirection);
	float diffuse = max(0.0,dot(normalizedNormal, normalizedLightDirection));
	vec4 diffuseColour = diffuse * gl_FrontMaterial.diffuse * gl_LightSource[0].diffuse;
	
	vec3 normalizedEyeVector = normalize(eyeVector);
	vec3 reflectedRay = reflect(-normalizedLightDirection, normalizedNormal);
	
	float specular = pow(max(dot(reflectedRay, normalizedEyeVector), 0.0), gl_FrontMaterial.shininess);
	vec4 specularColour = specular * gl_FrontMaterial.specular * gl_LightSource[0].specular;
	
	textureColour[0] *= (diffuseColour + specularColour);
	textureColour[1] *= (diffuseColour + specularColour);
	textureColour[2] *= (diffuseColour + specularColour);
	
	//this is the actual shadow mapping (including the magic bias)!
	vec3 realShadowMapPosition = shadowMapPosition.xyz/shadowMapPosition.w;
	float depthSm = texture2D(depthMap, realShadowMapPosition.xy).r;
	
	if (depthSm < realShadowMapPosition.z-0.0001)
	{		
		textureColour = vec4(0, 0, 0, 1);
	}
	gl_FragColor= textureColour;
}