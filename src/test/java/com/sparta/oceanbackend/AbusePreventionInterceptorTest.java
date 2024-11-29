//package com.sparta.oceanbackend;
//
//import com.sparta.oceanbackend.api.abuse.service.BlacklistService;
//import com.sparta.oceanbackend.api.abuse.service.RedisRateLimiterService;
//import com.sparta.oceanbackend.api.handler.AbusePreventionInterceptor;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.PrintWriter;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class AbusePreventionInterceptorTest {
//
//    @Mock
//    private RedisRateLimiterService rateLimiterService;
//
//    @Mock
//    private BlacklistService blacklistService;
//
//    @InjectMocks
//    private AbusePreventionInterceptor interceptor;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private PrintWriter writer;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//        when(response.getWriter()).thenReturn(writer);
//    }
//
//    @Test
//    @DisplayName("블랙리스트에 있는 IP인 경우 429 응답을 반환한다.")
//    void testPreHandle_BlacklistedIp() throws Exception {
//        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
//        when(blacklistService.isBlocked(anyString())).thenReturn(true);
//
//        boolean result = interceptor.preHandle(request, response, new Object());
//
//        assertFalse(result);
//        verify(response).setStatus(429);
//        verify(writer).write("너무 많은 요청을 보냈습니다. 잠시 후 다시 시도해주세요.");
//    }
//
//    @Test
//    @DisplayName("블랙리스트에 없는 IP 이고, 요청 횟수가 제한을 초과하지 않은 경우 true 를 반환한다.")
//    void testPreHandle_NotBlacklistedIp() throws Exception {
//        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
//        when(blacklistService.isBlocked(anyString())).thenReturn(false);
//        when(rateLimiterService.isRateLimited(anyString())).thenReturn(false);
//
//        boolean result = interceptor.preHandle(request, response, new Object());
//
//        assertTrue(result);
//    }
//
//    @Test
//    @DisplayName("블랙리스트에 없는 IP 이고, 요청 횟수가 제한을 초과한 경우 429 응답을 반환한다.")
//    void testPreHandle_RateLimitedIp() throws Exception {
//        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
//        when(blacklistService.isBlocked(anyString())).thenReturn(false);
//        when(rateLimiterService.isRateLimited(anyString())).thenReturn(true);
//
//        boolean result = interceptor.preHandle(request, response, new Object());
//
//        assertFalse(result);
//        verify(response).setStatus(429);
//        verify(response.getWriter()).write("너무 많은 요청을 보냈습니다. 잠시 후 다시 시도해주세요.");
//    }
//}
