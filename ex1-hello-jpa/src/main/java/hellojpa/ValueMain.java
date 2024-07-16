package hellojpa;

public class ValueMain {
    public static void main(String[] args) {

        int a = 10;
        int b = a;

        System.out.println("a == b: " + (a == b)); // 기본타입 true

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("address1 == address2:: " + (address1 == address2)); // false

        /**
         * 그냥 equals를 사용하면 false가 나온다.
         * 클래스 내부에 equals를 오버라이딩 해주어야 한다.
         *
         * 이런식으로 값타입의 비교는 꼭 equals를 사용해야 함.
         */
        System.out.println("address1 equals address2:: " + (address1.equals(address2))); // true
    }
}
