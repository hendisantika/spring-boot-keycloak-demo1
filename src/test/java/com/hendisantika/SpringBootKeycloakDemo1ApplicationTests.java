package com.hendisantika;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Base64;

//@SpringBootTest
@Slf4j
class SpringBootKeycloakDemo1ApplicationTests {

    @Test
    void decodeJWT() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHdDM5SUl2dUFBOGNXWVdCRG1KRnhROUFuMG9CMjRDMVdXYjM5bGlIS1o4In0.eyJleHAiOjE2NDI1MDg3ODUsImlhdCI6MTY0MjUwODE4NSwianRpIjoiNmU0ZTZkZWUtYjZlNC00NWIzLTgyZWMtYTc4MWY0ODllODNjIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL1Bvd2VyUmFuZ2VyIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImQ5MzU2M2ZlLTg0MTMtNGM1OS1hYzU0LWQzN2RjODYxNGQ0OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZ2Jvb3Qta2V5Y2xvYWsiLCJzZXNzaW9uX3N0YXRlIjoiOGQ3NDUzYjgtMjUwYS00ZTcyLWIyMWItMjBjNzFiNmM0M2M4IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLXBvd2VycmFuZ2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiOGQ3NDUzYjgtMjUwYS00ZTcyLWIyMWItMjBjNzFiNmM0M2M4IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwaG9uZSI6IjA4MTMyMTQxMTUxMSIsImRvYiI6IjE5OTktMDktMTAiLCJuYW1lIjoiZmlyc3ROYW1lIGxhc3ROYW1lIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlcjEzIiwiZ2l2ZW5fbmFtZSI6ImZpcnN0TmFtZSIsImZhbWlseV9uYW1lIjoibGFzdE5hbWUiLCJlbWFpbCI6Im5hcnV0bzEzQHlvcG1haWwuY29tIn0.CohWg491CaRGxbydQkM67_2Z7ANrRFL2YS6lwqzX2um-5xprdT8Pp3JqjGWA0mHgSjOOu4NfRAXpCpxokZ98wq0ZXvQnX3bmuXijl12qnZjTHs_InuXDFjRPxZDLRmUnXIqlm3k-vReVJIRvl59rh0AlAfCv2hfSy-kN8h9pu_-tjEP2RVgd7wX3tMbKBjYeQDqb1CYtUKy5qkKIdFcHm-eMTn6QMkQD348ZlnI0YsS2KxCL1U7AoS6uwQDAasR41f0GpApmnBUrAJ4D0ZkezFd-r8JpeS37qSvm3VbKynQnzX6kBjsFQ93wUxQsABho3rqAVlhViyZGVM3J0Vx8Iw";
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        log.info("Header: {}", header);
        log.info("Payload: {}", payload);
    }

}
