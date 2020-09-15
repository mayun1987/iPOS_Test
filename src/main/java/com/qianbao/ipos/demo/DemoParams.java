/**   
* @Title: CommonConstant.java 
* @Package com.demo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yanghj@bailianpay.com   
* @date 2019年4月11日 下午6:06:53 
* @version V1.0   
*/
package com.qianbao.ipos.demo;

import com.zrj.pay.util.secure.RSA;


/**
* @ClassName: CommonConstant 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yanghj@bailianpay.com
* @date 2019年4月11日 下午6:06:53 
*  
*/
public class DemoParams {
	public static final String agentCode  ="888888";
	public static final String publicKey  ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCw7O6jqxbiWY3g1HNsuFvjb6i+DkuGPlib7ACq03XOEpkaK+udtzDbX0EUghQJJRzxcpjjKP4Cz9MNJs4APnjCqMMenvNKm4qLLtB8gFYZy5goWAO5NdHD9vuH7H0WVFbv5FAdgsNMB7fwKLWZdUMwAYQAosMTQvj8I9x1wiWFFwIDAQAB";
	public static final String privateKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOG/PZWEtjaOJL+S6lyf3fLGbWKi/REre7h1r0UQMuBO0aRxZwjejpxMk3wV1TxOh54yZVoM4u8pDm5o5f2XVPtd7TAQkVHDn/r+j11JmbHSDJwu/Kc/S9xLU8CFcO+2coX4pNgsn3UKY6psjnT8zK4v7YqybxiOK2kgFht8qYtdAgMBAAECgYBm11iW5P6dm+Ph4mwiBjJ2hy8N4HKpf8W9It/V9qs2xLF82Ky3vav6SGhn/wpFjwD5cLv7aiN4EJwicGdWP7rWbZ0xGGE8oz5fFKsC5PTcwDCgJfUJCj/YyQa1hmZ2EWxZNf1EQzH2WaKCpyazK4sPwmVXe2Uo1xO7787HRSptgQJBAPpNZ38tK8Xkhti9+8NbMRPnQcdL01N3C1AqMK9jkxRDPaAEgTbwC6i697rf1QxmfcswBHb8PuZ3FUMaSTh5RnMCQQDm4r6BUdvefsuDS9QUchKIzP+qLzCrRmKaTyozpsXKxdP8yhhlcXTOYErk6WQ9ymEeUTH9d3p56WUeeDGUtKLvAkA876k5sfKNK/SUQbRQPBw3nz5JWbFqCXpMmujdWh2Nek0/brlSeaAtBM+YxDNm4HDRjbAYk5Jwox8fBgA7anL3AkEAnOcwYX+4tH5kXUiVyoMu90qg4A2LvIlrjsQHwOwv9Dn2cw7//0hYAbvMib3WAmvbDhhNqZudXuXucwNpZk5PfwJBAOq7yufcTlatuaXjW8+Sj7Sa5T0mwY7Lc8QwaVMXglZNu3cx3s/AARx3pZyggZSFMRee31lDhOTdnLBvnaxkrRA=";
	
	public static void main(String[] args) throws Exception {
		String myPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhvz2VhLY2jiS/kupcn93yxm1iov0RK3u4da9FEDLgTtGkcWcI3o6cTJN8FdU8ToeeMmVaDOLvKQ5uaOX9l1T7Xe0wEJFRw5/6/o9dSZmx0gycLvynP0vcS1PAhXDvtnKF+KTYLJ91CmOqbI50/MyuL+2Ksm8YjitpIBYbfKmLXQIDAQAB";
		String myprivateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALDs7qOrFuJZjeDUc2y4W+NvqL4OS4Y+WJvsAKrTdc4SmRor6523MNtfQRSCFAklHPFymOMo/gLP0w0mzgA+eMKowx6e80qbiosu0HyAVhnLmChYA7k10cP2+4fsfRZUVu/kUB2Cw0wHt/AotZl1QzABhACiwxNC+Pwj3HXCJYUXAgMBAAECgYBUo4QmWBrCfgUZoYj2YdvTmf8nrL8EoZ7SOLy4/CBHTS5hDtHQyWpeB9uia/gZx99owmHRijK6NaHJkN7LzxcFe1awUOqXB0AWnpxTqupsUqs7W/3/kOKKoBhmiVLTNOmKpEdHXPpxDXUFkkp5DMeB4MfkLHYPfgSKBpDyzRw6QQJBAPIKZJCchfOtOZgEHu0OiY7+ZYLDqDKD1CWkgE+ZFZWqaEcwj/meoOVpaQ1KN/RH4/ra+dFW4E61MkeYy+xcI50CQQC7ISXoO2X87AiiVhzYu7arlnIZsoxZ+HdGkdTXzPjogc/xoqA/o+7wnKELvds0z4qrEDI44C4WQ9p+C2jKGg9DAkEAnxZ9qx92XAgLxwNOYHohqD9F8YEw2KMVmfhZ7fes3Ea5a7FbsLK3BpMWXYne5U0raALuGM6FeLZYaf7gambVcQJAMTVsVH+aOEKRmq7ucwgZ51Fuu9c6FUbDhuk+gWlDY1EPr8lGPrYLVXa62u2YQXD6VDW0H0TEmdJKGK9DZTIpJQJAOdEzaZ4K84g+RiPbMJzXtyiL3r3jht8z6Of1yFnKLL49ITeWncN4zNNPBpITuAaRlY7bpDZ4nA+SIvplQd3aIA==";
		
		/*String miwen = RSA.encrypt("aaaaaaaaaaaaa", myPublicKey, "utf-8");
		System.out.println(miwen);
		String mingwen = RSA.decrypt(miwen, privateKey, "utf-8");
		System.out.println(mingwen);
		String miwen1 = RSA.encrypt("长啥你萨克萨拉", publicKey, "utf-8");
		System.out.println(miwen1);
		String mingwen1 = RSA.decrypt(miwen1, myprivateKey, "utf-8");
		System.out.println(mingwen1);*/
		String sign = RSA.sign("aaaaaaaaaaaaa", privateKey, "utf-8");
		System.out.println(RSA.verify(sign, "888888VkdnwX7WH3AGAPoUkdfuzqpBdQnkggNhfoCRGmXlfA5tYOiZcN5A i9gcLUewtpAbt7yWxk Ig61wJ4vt2f5bogxULf3H1uVABEqzHDkGMoIQH//FFB zLm1RCwYlg0j/zpQj/M7DQqNAiahHOL tSF4Tgy0rx0 E1AmT7gAATo=RfuJJj//BFIpRnLuXevsNGlq58F020iDieC2sU0l9yB4SgFio20CHHjH41S0lMFl", myPublicKey, "utf-8"));
		
	}
}
