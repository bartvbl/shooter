#extension GL_EXT_gpu_shader4 : enable 
#extension GL_ARB_draw_buffers : enable

//INPUTS
//these are standard shadow map coordinates
varying vec4 shadowMapPosition;
varying vec3 normal;
varying vec3 worldPos;
varying vec3 lightDirection;
varying vec3 eyeVec;

//uniforms
uniform sampler2D depthMap;
uniform vec4 LightPosition;
uniform sampler2D texture0;

void main(void)
{
	vec4 final_color = 
	(gl_FrontLightModelProduct.sceneColor * gl_FrontMaterial.ambient) + 
	(gl_LightSource[0].ambient * gl_FrontMaterial.ambient);
							
	vec3 N = normalize(normal);
	vec3 L = normalize(lightDirection);
	
	float lambertTerm = dot(N,L);
	
	if(lambertTerm > 0.0)
	{
		final_color += gl_LightSource[0].diffuse * 
		               gl_FrontMaterial.diffuse * 
					   lambertTerm;	
		
		vec3 E = normalize(eyeVec);
		vec3 R = reflect(-L, N);
		float specular = pow( max(dot(R, E), 0.0), 
		                 gl_FrontMaterial.shininess );
		final_color += gl_LightSource[0].specular * 
		               gl_FrontMaterial.specular * 
					   specular;	
	}

	gl_FragColor = final_color;
	
	
	//vec4 fragmentColour = texture2D(texture0, gl_TexCoord[0].st);
	
	//float diffuse = max(0.0,dot(normal, lightDirection));
	//vec4 diffuseColour = diffuse * gl_LightSource[0].diffuse * gl_FrontMaterial.diffuse;
	//fragmentColour.rgb *= diffuse;//Colour;
	
	//vec3 inversePosition = normalize(-worldPos);
	//vec3 reflection = reflect(-lightDirection, normal);
	//float specular = pow(max(0.0, dot(reflection, inversePosition)), gl_FrontMaterial.shininess);
	//vec4 specularColour = specular * gl_LightSource[0].specular * gl_FrontMaterial.specular;
	//fragmentColour *= specularColour;
	
	//this is the actual shadow mapping (including the magic bias)!
	//vec3 realShadowMapPosition=shadowMapPosition.xyz/shadowMapPosition.w;	
	//float depthSm = texture2D(depthMap, realShadowMapPosition.xy).r;
	
	//if (depthSm < (realShadowMapPosition.z-0.0001))
	//{		
	//	fragmentColour = vec4(0, 0, 0, 1);
	//}
	
	//gl_FragColor= fragmentColour;
}