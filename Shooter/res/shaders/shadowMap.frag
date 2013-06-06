#extension GL_EXT_gpu_shader4 : enable 
#extension GL_ARB_draw_buffers : enable

//INPUTS
//these are standard shadow map coordinates
varying vec4 shadowMapPosition;
varying vec4 viewPosition;
varying vec3 normal;
varying vec3 worldPos;
varying vec4 Color;

//uniforms
uniform sampler2D depthMap;
uniform vec4 LightPosition;
uniform sampler2D texture0;

void main(void)
{
	float light=max(0.0,dot(normalize(LightPosition.xyz-worldPos),normalize(normal.xyz)));
	vec4 textureColour = texture2D(texture0, gl_TexCoord[0].st);
	//this is the actual shadow mapping (including the magic bias)!
	vec3 realShadowMapPosition=shadowMapPosition.xyz/shadowMapPosition.w;	
	float depthSm = texture2D(depthMap, realShadowMapPosition.xy).r;
	
	if (depthSm < realShadowMapPosition.z-0.0001)
	{		
		light = 0.0;
	}
	
	textureColour[0] *= light;
	textureColour[1] *= light;
	textureColour[2] *= light;
	
	
	
	
	gl_FragColor= textureColour;//vec4(depthSm, depthSm, depthSm,1);//textureColour;
}