#extension GL_EXT_gpu_shader4 : enable 
#extension GL_ARB_draw_buffers : enable

//INPUTS
//these are standard shadow map coordinates
varying vec4 smPos01;
varying vec4 viewPos01;
varying vec3 normal;
varying vec3 worldPos;
varying vec4 Color;

//uniforms
uniform sampler2D DepthMap;
uniform vec4 LightPosition;
uniform sampler2D texture0;

void main(void)
{
	


	float light=max(0.0,dot(normalize(LightPosition.xyz-worldPos),normalize(normal.xyz)));
	vec4 textureColour = texture2D(texture0, gl_TexCoord[0].st);
	//this is the actual shadow mapping (including the magic bias)!
	vec3 realSmPos=smPos01.xyz/smPos01.w;	
	float depthSm = texture2D(DepthMap, realSmPos.xy).r;
	if (depthSm < realSmPos.z-0.01)
	{		
		//light=0;
	}
	textureColour[0] *= light;
	textureColour[1] *= light;
	textureColour[2] *= light;
	
	gl_FragColor= textureColour;
}