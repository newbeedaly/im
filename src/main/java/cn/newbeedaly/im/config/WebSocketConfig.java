package cn.newbeedaly.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * WebSocket 和 STOMP（Simple Text Oriented Messaging Protocol，简单文本导向消息协议） 消息的配置类。
 * 启用 WebSocket 消息处理，配置消息代理和端点，并设置跨域资源共享 (CORS) 以支持前端访问。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置 STOMP 消息代理。
     * 定义客户端与服务器之间的消息路由规则。
     *
     * @param config 消息代理注册对象，用于设置代理参数。
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用简单的内存消息代理，将消息广播到以 "/topic" 开头的目标地址。
        // 例如，发送到 "/topic/room1" 的消息将被广播到订阅该地址的所有客户端。
        config.enableSimpleBroker("/topic");

        // 设置客户端发送到服务器的消息前缀。
        // 客户端发送到以 "/app" 开头的地址（如 "/app/chat/room1"）的消息将由 @MessageMapping 方法处理。
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * 注册 STOMP 端点用于 WebSocket 连接。
     * 定义客户端连接 WebSocket 的端点地址。
     *
     * @param registry STOMP 端点注册对象，用于注册 WebSocket 端点。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 "/chat-websocket" 端点，客户端通过此端点建立 WebSocket 连接。
        // setAllowedOrigins 允许来自 "http://localhost:8000" 的跨域请求（前端地址）。
        // withSockJS() 启用 SockJS 后备选项，支持不兼容 WebSocket 的浏览器。
        registry.addEndpoint("/chat-websocket")
                .setAllowedOrigins("http://localhost:8000")
                .withSockJS();
    }

    /**
     * 配置全局 CORS 设置以支持 WebSocket 握手。
     * 确保前端（http://localhost:8000）可以访问后端 WebSocket 端点。
     *
     * @return CorsConfigurationSource CORS 配置源对象。
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 创建 CORS 配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许来自 "http://localhost:8000" 的跨域请求
        configuration.addAllowedOrigin("http://localhost:8000");
        // 允许所有 HTTP 方法（GET、POST 等）
        configuration.addAllowedMethod("*");
        // 允许所有请求头
        configuration.addAllowedHeader("*");
        // 允许携带凭据（如 Cookies）
        configuration.setAllowCredentials(true);

        // 创建基于 URL 的 CORS 配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 将 CORS 配置应用到所有路径（/**）
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}