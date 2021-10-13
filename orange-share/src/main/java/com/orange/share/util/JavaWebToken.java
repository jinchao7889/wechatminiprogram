package com.orange.share.util;




public class JavaWebToken {
//    private static Logger log = LoggerFactory.getLogger(JavaWebToken.class);
//
//    //该方法使用HS256算法和Secret:bankgl生成signKey
//    private static Key getKeyInstance() {
//        //We will sign our JavaWebToken with our ApiKey secret
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("bankgl");
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//        return signingKey;
//    }
//
//    //使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
//    public static String createJavaWebToken(Map<String, Object> claims) {
//        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
//    }
//
//    //解析Token，同时也能验证Token，当验证失败返回null
//    public static Map<String, Object> parserJavaWebToken(String jwt) {
//        try {
//            Map<String, Object> jwtClaims =
//                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
//            return jwtClaims;
//        } catch (Exception e) {
//            log.error("json web token verify failed");
//            return null;
//        }
//    }

//
//    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
//    public static final String SECRET = "imooc";
//    /** token 过期时间: 10天 */
//    public static final int calendarField = Calendar.DATE;
//    public static final int calendarInterval = 10;
//
//    /**
//     * JWT生成Token.<br/>
//     *
//     * JWT构成: header, payload, signature
//     *
//     * @param user_id
//     *            登录成功后用户user_id, 参数user_id不可传空
//     */
//    public static String createToken(Long user_id) throws Exception {
//        Date iatDate = new Date();
//        // expire time
//        Calendar nowTime = Calendar.getInstance();
//        nowTime.add(calendarField, calendarInterval);
//        Date expiresDate = nowTime.getTime();
//
//        // header Map
//        Map<String, Object> map = new HashMap<>();
//        map.put("alg", "HS256");
//        map.put("typ", "JWT");
//
//        // build token
//        // param backups {iss:Service, aud:APP}
//        String token = JWT.create().withHeader(map) // header
//                .withClaim("iss", "Service") // payload
//                .withClaim("aud", "APP").withClaim("user_id", null == user_id ? null : user_id.toString())
//                .withIssuedAt(iatDate) // sign time
//                .withExpiresAt(expiresDate) // expire time
//                .sign(Algorithm.HMAC256(SECRET)); // signature
//
//        return token;
//    }
//
//    /**
//     * 解密Token
//     *
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Claim> verifyToken(String token) {
//        DecodedJWT jwt = null;
//        try {
//            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
//            jwt = verifier.verify(token);
//        } catch (Exception e) {
//             e.printStackTrace();
//            // token 校验失败, 抛出Token验证非法异常
//        }
//        return jwt.getClaims();
//    }
//
//    /**
//     * 根据Token获取user_id
//     *
//     * @param token
//     * @return user_id
//     */
//    public static String getAppUID(String token) {
//        Map<String, Claim> claims = verifyToken(token);
//        Claim user_id_claim = claims.get("userId");
//        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
//            // token 校验失败, 抛出Token验证非法异常
//        }
//        return user_id_claim.asString();
//    }
}
