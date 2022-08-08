TDD 작성

- [참고블로그](https://blog.kingbbode.com/52?category=737337)

## 요구사항 정리

1. 제목(##)과 `- [ ]` 박스를 만든다.
2. 키워드를 추출한다
3. 객체 vs 메서드 vs UI vs 세부요구사항으로 나눈다.
4. 관리 vs 객체 시작점을 고른다.


```markdown
## 요구사항
- [ ] 각 자동차에 이름을 부여할 수 있다.
- [ ] 전진하는 자동차를 출력할 때 자동차 이름을 같이 출력한다.
- [ ] 자동차 이름은 쉼표(,)를 기준으로 구분한다.
- [ ] 자동차 경주 게임을 완료한 후 누가 우승했는지를 알려준다. 우승자는 한명 이상일 수 있다.

## 키워드 -> 분류 -> 객체 vs 메서드 vs 세부요구사항 vs ui 나누기 -> 시작점 고르기

- 자동차
    - 이름 : object
        - , 기준으로 구분 : 세부요구사항 - ui 맨 나중에
    - 전진 : method
- 자동차 경주 게임
    - 완료한 후 누가 우승했는지를 알려준다. : 메서드 
- 우승자 
    - 한명 이상 : 세부요구사항
```



## 테스트

### 테스트 작성요령

1. **시작점을 고를 때, **

   1. **`기능(메서드)을 가진 데이터객체`이외에 **

   2. **controller에서 `정제된 input이 들어오고, controller로 output응답도 해야하는 Service`부터 시작할 수 있다.**

      - **정제된 input부터 -> 메인 흐름을 다 나열 -> 출력될 ouput객체까지 반환해주는 것이 service의 역할이다.**
        - 처음부터 객체도출은 어려울 수 있다.
      - 서비스의 메서드부터 짠다면, **input 부터 output까지 메인 흐름을 생각하고 정리**해야한다
      - 서비스 start~end까지 로직이 짜여졌으면 `해당 로직에 맞는 메서드명`으로 변경한다

   3. **service에 대한 input**이 **`내부 로직에 의해 데이터객체`로 주어진다면**, **`컴퓨터가 줄 데이터객체로부터 만들`어놓고, `그것을 이용한 input으로 한 -> output을 내는 핵심로직`부터 시작한다.**

      - 예를 들어, 블랙잭게임을 하면, 카드덱으로부터 카드2장이 주어진다. 
        - **이미 `카드 2장이 주어졌다고 가정`하고 `데이터객체 카드`부터 만들고 `핵심 로직으로서 시작`하자.**
      - **service가 핵심로직이 아닐 수 있다. `서비스내 Game등이 핵심로직`일 수 있다.**
      - **input -> output이 나오는 핵심로직부터 시작한다. `Game`.`start()`**로 시작하자.

      ![image-20220803171440490](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803171440490.png)

   4. **핵심로직 자체도 service가 아니라 (상태패턴이라면) 상태를 가지는 객체로 바뀔 수 있다.**

      - **`서비스 메서드 == 스태틱 메서드 like 유틸메서드`에서 -> `객체`가 되었다면**

        - **상태값을 가지며, 상태값을 만드는 정보input들은 다 생성자로 들어와야한다.**
        - **메서드로 상태값 정보가 들어온다면, setter다.**

      - **서비스의 `유틸메서드 input` -> `생성자로 주입` 된다**

      - **서비스의  `유틸메서드내 로직을 거친 뒤 1개의 output응답값(상태객체)` -> `필드`로 가진다.**

        - **상태객체는, 정보만 가져야하므로 `상태객체가 아닌 상태객체를 사용하는 객체`일뿐이다.**

        - **상태객체로 다시 변환하려면 `다른 상태객체들과 상태값(필드) 와 메서드를 동일`하게 사용하도록 수정해야한다.**

          - Ready도 **다른 상태객체들과 `동일 상태값 필드`(Cards)**를 가지며 **`생성자로는 아무것도 주입 안된 빈카드로 초기화`하는 `기본 생성자`로 만들어져야한다.**

          - Ready도 .start()로 다른 객체로 넘어가는 것이 아닌 **다른 상태객체들의 메서드처럼 `Trigger메서드(draw())`로부터 setter정보를 받아 `상태값업데이트 이후 상태값을 가지고 판단하여 다른 상태로 넘어가`도록 수정한다.** 
            - **Ready는 카드를 2장을 받는다. -> 다른상태객체처럼 draw로 1장씩 받도록 정의하고 `외부에서 2번을 호출`하던지 `다른 파라미터로서 받도록 오버로딩메서드`로 정의해준다.**

      - **서비스 or 핵심로직  통합테스트가 Ready라는 1개 상태객체 테스트로 바꼈다.**

        - **개별 상태객체로 테스트코드들을 나누어서 옮긴다.**

2. **정제된 input(원시값)을 통해 service개발을 완료하고 나면**

   1. **`가장작은 단위의 input`에 대한 `서비스에서 예외발생`테스트를 먼저 만들고 -> `필요에 의해 도메인 객체에서 예외발생 되도록 도메인을 생성한다`**
      1. **`원시값input을 도메인객체로 만들면서 사전검증`이 이루어지도록 해야한다.**
         1. **검증이 필요한 원시값에 new도메인()을 때려서 시작한다**

   - **그 다음에 `컬렉션을 일급컬렉션으로 만들면서 중복 검증 등`의 검증이 이루어져야한다.**

3. **가장 빠르게 실패는 thr IllegalStateException을 활용하고. 가장 빠르게 성공은 로직 없이 응답값만 return**하도록 만든다.

   - 실패 -> **성공 -> 리팩토링** 을 반복한다.

4. **생성자로 시작 이외에 `나중엔 (상태필드 변경후 상태로 확인) 바뀌더라도 상수 응답 메서드`로 먼저 테스트를 작성한다.**

   - car.move()는 setter같은 메서드인데, **처음 메서드 작성시 1칸 전진마다 상수1을 응답값으로 주도록 만든다**
   - **`응답도 getter로서 조회의 일`이다. `조회용 메서드가 아니라면, 로직 테스트 완료후 응답 대신 상태로 확인`하도록 변경해야한다.**
     1. 조회용 메서드가 맞는지 확인한다.
     2. 조회용 메서가 아니라면 **getter같은 return응답을 제거한다.**
     3. **기존에 메서드결과로 응답값을 조회했던 부분을 `값 비교 -> 객체 비교`를 하도록 `eq/hC 재정의 및 테스트 assert문 수정`을 해줘야한다.**
   - **`상태가 변하는 메서드는 setter`이므로 `불변객체를 유지하기 위해, 상태값 변화 대신, 상태값이 변화한 객체 응답메서드`로 바꾸자.**
     - 다시 한번 **응답메서드가 되지만, 상수 응답이 아닌 `새 객체를 만들어 반환`이다.**
     - **객체의 상태값 및 생성자 등이 바뀌므로 `2메서드가 아니라 2객체를 생성해서 테스트`한 뒤 반영한다.**

5. **테스트할 메서드는 `무조건 응답하도록 먼저 작성`하며 이 때, `응답값은 [넣어준 인자에 대한 case값을 응답]`을 해줘야한다**

   - 만약, 로또번호 vs 당첨번호 **인자 입력을 1등 번호로  예시case로 넣어줬다면**, 그 **인자 case에 맞는 1등이 응답값으로 반환해야한다(아무거나 반환X)** 
     - 2번째부터는일반화하는 경우가 많으니, **`첫 메서드 생성하는, 1번째 첫 인자를 입력할 때, case에 맞는 반환도 생각하고 입력`하자**
   - **`TDD메서드의 응답값은 테스트메서드의 테스트CASE에 의해 작성순서가 결정`된다.**

6. case별로 테스트 메서드를 만든다. `메서드명_CASE____세부내용`형태를 빌린다.

   - **2번째case부터는 일반화**해도 좋다
   - **case == 메서드의 인자가 결정**

7. **테스트케이스 추가에 따른 `기능추가`, `사전 검증과 같이 전체메서드가 걸린 기능추가`는 **

   1. **메서드를 전체를 2메서드로 복사해놓고 테스트**
   2. **기존 테스트들을 2메서드로 호출하도록 변경 후 전체 테스트**
      - 이 때 `기존테스트()`의 소괄호까지를 `ctrl + h`로 찾아서 변경하면 쉽게 변경된다.
   3. **기존테스트들 다 통과시 `회색으로 안쓰게 된 원본메서드 안전삭제(alt+del)`**
   4. **2메서드를 -> 원본메서드로 변경**

8. **기능 추가 -> 2case부터 일반화로직 추가시, 1case를 보존하기 위해,  `2메서드 복붙후 테스트에서 1case 인자를 그대로 사용` + 내부로직의 결과값에 if를 사용하더로 먼저 통과시키게 해야한다.**

9. **특정로직(private메서드)에 대한 기능 추가는 `private메서드만 복사해서 테스트`해놓고 끝나면 반영하고 삭제한다.**

10. **요구사항이 복잡해질 땐,  그 부분만 아래에서 학습테스트를 진행해도 된다.**

11. **상태변경 메서드의 테스트가 끝나고 다른 기능 테스트를 할 때, `특정 상태를 만들기 위해 상태변경메서드 반복 호출`을 하지말고, `직접 해당 상태로 만드는 생성자를 추가`해서 현재하는 테스트에 집중하게 한다**

    - 특정상태의 객체를 불변객체로 바로 만들면, 빈컬렉션+add가 사라진다.

12. **기존파라미터(원시 컬렉)를 살려두고 이어서갈 땐 `새 인자(도메인 컬렉)로 메서드 작성후 오버로딩`**

    - **기존 파라미터(도메인 컬렉)를 변환된 파라미터(일급컬렉션)로 대체하고 싶다면 `내부변환후 파라미터 추출`**

    ![image-20220728230052251](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728230052251.png)

13. **서비스내 `getter`가 보이면, `도메인 내부 로직`으로서 `캡슐화로 감춰야하는 로직`임을 100% 생각한다.**

    - 출력을 제외하고 getter는 없다고 보자.
    - **getter이후가 같은형의 비교면 -> `해당형으로 책임을 위임해 옮긴다`**
    - **getter이후가 다른형의 비교면 -> `제3형을 만들어 책임을 위임한다.`**
    - static메서드였으면, `static을 삭제하고 -> 메서드 이동 -> 파라미터 중 위임할 객체가 2개가 같은형이면, 하나의 변수를 택1`한다

14. 같은형의 일급vs일급은  -> 일급vs단일(contains) 메서드를 사용할 확률이 높다. 확인해서 처리한다.

15. **정해진 갯수의 인스턴스는 `정팩메를 통한 캐싱`을 도입하자**

16. **정팩메든 생성자든 파라미터를 추가하면 견고한 클래스가 된다.**

    - **자바(부생성자개념)이외 언어들은 `주생성자로만 생성`하고 `다양한 타입의 파라미터로 생성하려면 정팩매`를 이용해야한다.**
    - **나도 앞으로 `파라미터 추가를 위해서 정팩매 도입`을 고려해봐야겠다.**

    ![image-20220731152352680](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731152352680.png)

17. **일급컬렉션도 `빈컬렉션으로 시작하여 add시 새객체반환`하는 일급컬렉션으로 만든다.**

    - **new 빈컬렉션을 인자로 받는 `부생성자`를 만들어주면 된다.**

18. **기존에 생성자 없이 사용하던 객체에, `상태값이 추가되어 생성자가 추가될 경우`, `부생성자로 빈값할당`으로 초기화해주는 `기본생성자`를 추가해서 `기존코드가 망가지게 않게 한다`**

19. **`여러종류의 클래스객체 응답이 가능한 메서드`에 대해 특정 클래스의 객체를 응답하는 테스트를 작성하려면, Object actual -> isIntanceof(특정클래스.class)로 테스트코드를 작성한다.**

20. **여러 종류의 객체return는 `추상체 응답의 상태패턴`을 고려한다.**

    ![image-20220804113722642](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804113722642.png)

    - **상태패턴을 도입했으면, `응답값으로 나온 현재 상태`를 바탕으로 `추상체에 있는 [다음상태로 갈 수 있는 인자]를 받아 [다음상태로가는 메서드]`를 정의해줘야한다. `에러호출로 종료되는 제일 쉬운 현재상태(blackjack)부터 [다음 상태로 넘어가는 메서드]를 만들자`**

21. **추상체를 만드는 순간부터 `패키지를 분리하여, 패키지폴더 대상 다이어그램으로 의존성을 확인`하자.**

    - **이후, `구상체만의 메서드 개발후 올리기전 다이어그램 + CompareFile하자`**

22. **`상태변화가 없는 객체`는 `캐싱객체` or` VO로, eq/hC를 재정의하여 값이 같으면 같은 객체가 되는 값객체`라서 `testutil패키지 > Fixtures클래스`안에 `상수객체`로 만들어 써도 된다.**
    - test루트에서 **`testutil` 패키지를 만들고 내부 `Fixtures`를** 만들자
    - **Fixtures에 쓸려면 `어디서든 불러도 같은 객체가 생성되도록 캐싱==싱글톤이 보장`되어야한다.**
      - **`캐싱객체`는 test코드에서 `isSameAs`**로 **확인**해야한다.
      - **`eq/hC의 VO값객체`는 test코드에서 `eq/hC 재정의 후 isEqualTo`로 확인해야한다**
    - **상수객체 Fixture는 `한글로 알아보기 쉽게 네이밍`해도 된다.**

23. **상태패턴의 상태객체반환 with trigger되는 `결국엔 다른 객체의 상태값으로 포장`될 `객체`들은 `view에 넘기기 위해 getter를 무조건 가진다`**
    - **`trigger 등 set계열 메서드들 개발이 완료되면 getter도 개발`해야한다.**

24. **좋은 부모를 만들기 위한 전략(템플릿메소드) 중 `중복 필드 처리`**

    1. [Object)중복로직제거 Strategy to Templatemethod(Plan3)](https://blog.chojaeseong.com/java/%EC%9A%B0%ED%85%8C%EC%BD%94/oop/object/strategy/templatemethod/plan/connectobject/2022/07/12/Object_strategy_to_template_for_duplicate.html#next%ED%95%84%EB%93%9C%EB%A1%9C-%EC%97%B0%EA%B2%B0%EB%90%98%EB%8A%94-%ED%95%A9%EC%84%B1%EA%B0%9D%EC%B2%B4%EC%9A%A9-%EC%83%9D%EC%84%B1%EC%9E%90-%EC%A3%BC%EC%9E%85-%EB%8C%80%EC%8B%A0-void-setter%EB%A5%BC-%EA%B0%80%EC%A7%84-%EC%B6%94%EC%83%81%ED%81%B4%EB%9E%98%EC%8A%A4%EC%97%90-%EA%B5%AC%EC%83%81%EC%B8%B5-%EA%B0%9D%EC%B2%B4%EB%B3%84-%EC%B2%B4%EC%9D%B4%EB%8B%9D-%EB%B0%9B%EA%B8%B0%EA%B8%B0%EB%8A%A5%EC%9C%BC%EB%A1%9C-%EB%8C%80%EC%B2%B4%ED%95%98%EA%B8%B0)
       1. **추상클래스는 좋은 상속의 부모로서 `변수는 private`, `메서드는 final or protected abstract`, `생성자없이 setter로`**확인하여 달아준다.
    2. **`구상체별 중복되는 필드`를 올릴 땐, 구상클래스 `생성자 주입` -> `setter메서드 -> private필드`로 올린다.**
    3. **부모의  private필드는 부모내에서 처리하게 하고, `자식은 부모private필드자체는 못쓰고, final 물려주는 템플릿메소드들만 쓴다.`**
       - 자식들이 훅메서드 구현시 필요한 정보들은, 자기가 생성자 주입받아서 쓰면 된다.
       - **자식이 부모의 private필드값을 써야한다면, `부모에게 구현된 getter를 물려받아 쓰면`된다.**

25. **암기getter**

    1. **`getter정의시 return 불변객체`라면, `public열어두기 가능 + dto없어도 됨`** 
       - **불변의 일급컬렉션 객체 반환 -> dto없는 public getter로 제공**
       - **불변 객체 아니라면, `Dto로 만들어서 반환`**
    2. **`getter정의시 return 컬렉션필드라면, view로 보낼 땐, 깊은 복사로 못건들게 해서 반환`**
       - **불변의 일급컬렉션 `자신 내부의 컬렉션 필드 반환`**
         - server사이드 반환이라면, 얕은복사 반환후, 내부에서 객체들 조작
         - **view반환이라면,  `깊은 복사 반환 or DTO로 반환`후, 내부 객체들 조작안되게**

26. **`중복제거 등 추상화/상속 관련작업`이 끝날 때마다, diagram을 보자**

    - **복잡하다면, `자물쇠의 show dependencis만 끄면` 상속관계가 잘 보인다.**

    - **추상화 레벨을 보고 싶다면, `바로 위의 추상클래스에 데고 usage(shift+F12)`를 통해 `어떤 놈들이 나를 직접 extends했는지`확인하면 된다.**

      ![image-20220806231001237](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806231001237.png)

      

27. **랜덤이 껴있는 로직은, 테스트를 고려해서 1개 메서드(함수형인터페이스)로 추출하고, 무조건 전략패턴으로 만들어야한다**

    - 추출할 메서드는 [전략객체-or]에 대해 -동사()를 쓴다 ex> Generator -> generate() / Applier -> apply()
    - 구상 전략객체에 해당 내수용메서드를 위임하기 위해선 [인자에 구상체 생성 -> class생성 -> 파라미터에 추가]해야지 F6으로 위임된다.
    - 내수용메서드들이 포함되어있다면, 모두 해당객체를 인자추가 -> 파라미터로 추가해놓자.
    - 내부 this를 사용했다면(위임전 객체의 context) -> 위임받을객체로 일단 바꿔주자.
    - 내부 context(상태값 등)이 사용되었다면 -> 추출 전 지역변수로 받아 추출하던지 or 추출후 파라미터추출해서, 파라미터에 포함되게 한다.
    - 다 만들어놨으면, 위임은 내부 && 먼저 호출되는 순으로 위임해야지, 기존context가 안잡힌다.
    - 위임과정에서는 내수용private -> public으로 된다. 위임후 수정해주자.
    - 구상 전략객체에서 @Override만 붙이면 extract interface가 가능하다.
    - 전략패턴이 되었으면 인터페이스에 @FunctionalInterface를 달아준다.
    - 테스트에선 람다식으로 만들되, 바로 못만들면, 시그니쳐 반환형에 맞춘 객체를 수동으로 만들어, 람다식에 넣어주는 식으로 한다.
        - 꼭 또다른 전략객체를 안만들어도 된다. test용 class라면 만들필요가 없다.
    	- 만드는 과정에 바뀌는 변수들을 파라미터 추출하고 메서드로 정의한다.
    	- 전략주입 객체까지를 하나의 Fixture로 볼 수 있다. [ 테스트용 수동전략 생성 + 전략적용객체 생성 ]까지를 1개의 fixture로 보고
    	    - 여러 테스트에서도 사용할 수 있다면, testutil패키지의 FixtureGenerator클래스에 유틸메서드(public static)로서 빼주면 된다.
    	





### 테스트 코드

1. split등으로 actual될 값이 2개이상이면, assertAll을 활용해서 0번인덱스부터 단계별로 확인하자
2. 상수 리팩토링은 result_index , result_prefix 등으로 case_result + 명칭을 그대로 붙여서 지어도 된다.
3. 메서드 안에서 또다른 일을 하는 경우,  **파라미터 -> 또다른 일 -> 반환 과정을 메서드추출하고, `파라미터로 올리면, 추출메서드가 현재메서드의 인자로 올라가서 일하고 있게된다.`**
4. 케이스 추가 -> 테스트메서드 추가 -> **메서드2 작성시 `개별 메서드 호출마다 저장소가 필요하다면, 메서드주체인 객체의 상태필드를 이용`해야한다. 필드를 만들어서 쓰자.** 
5. 메소드에 존재하는 테스트 힘든 코드는 확정값을 만들 수 있는 전략객체로 위임하되 전략객체는 협력객체로서 메서드 인자가 아닌 생성자 인자로 받아야한다
   - 프로덕션용 기본 전략값이 정해져있다면 **생성자 오버로딩을 이용**하자
6. getter에서 get을 뺀 뒤 필드처럼 지어서, 내부의 필드명이 없는 것처럼 감출 수 있다.
7. .setter처럼 상태변화 메서드가 만들어졌다면, **상태변화한 객체를 새로생성해서 반환하는 메서드로 변경한다.**
   - **필요시 생성자를 추가한다.**
   - **자신형 객체반환 메서드는 체이닝이 가능하니 테스트에서 체이닝으로 작성**해도 된다.
8. **`특정 상태를 만들기 위해 상태변경메서드 반복 호출`을 하지말고, `직접 해당 상태로 만드는 생성자를 추가`해서 현재하는 테스트에 집중하게 한다**
   - 특정상태의 객체를 불변객체로 바로 만들면, 빈컬렉션+add가 사라진다.
9. 컬렉션 인자 작성사 `List.of`는 **컬렉조작불가능한 얕은복사**을 사용하자.
   - `Arrays.asList`는 **조작가능한 생성자 복사(얕은 복사) 사본**

10. 인자가 3개이상이면, 엔터쳐서  줄바꿈해준다.
11. 컬렉션 vs 컬렉션 -> 반복문 + 요소 vs 컬렉션  + **저장/업데이트용 지역변수**가 생긴다
12. **`원시값 인자 포장`시, 검증대상에 new때려서 만들기 시작하며, 검증로직 작성 후, `전체 원시값인자들을 도메인 인자로 변경`하여 -> `도메인 파마리터 메서드`를 생성한 뒤, `원시값 파라미터 메서드 내부에서 도메인 전환후 오버로딩 호출`할 수있도록 로직을 변경한다.**
    - 테스트코드에서 도메인 인자들이 다 사라지고 원시값으로 다시 복구되었다면, **오버로딩의 도메인 파라미터 메서드는 private화 시켜서, 외부에서 객체 생성을 막는다.?!**
13. **`도메인 컬렉션 인자 포장`시 [이미 원시값 인자 존재 + 1개의 오버로딩 존재 + 도메인컬렉션 파라미터는 필요없을 때]**
    - **내부에서 `p1 -> 일급컬렉션 변환과정`까지 작성하고 새롭게 `파라미터 추출`해서 `파라미터 자체를 변경`한다.**
    - 원시값 포장시에는 **도메인입력 < 원시값입력이므로 `기존 파라미터 메서드를 살리는 방향으로 오버로딩 적용`을 했었지만, `도메인 컬렉션 -> 일급컬렉션`에서는 도메인컬렉션 입력을 죽인다.**

14. 일급컬렉션의 검증은 **도메인객체들의 `갯수, 중복여부`등이다.**
15. **같은형의 비교시 -> 내부 메서드로 돌아갈 때, `하나는 other라는 파라미터`명으로 잡아서 처리해준다.**
16. **getter를 포함한 로직을 위임**할 땐, 
    1. 메서드 추출 -> 위임받을 객체가 파라미터로 뽑혀야함
    2. **그외 보라색 내부context는 `위임객체 포함 모두 외부context -> 파라미터가 되도록 추출`해야함**
    3. **`static이 붙어있으면 반드시 삭제`(빨간줄감수)하고 이동해야함**
    4. 같은형이 2개가 파라미터에 있다면 알아서 선택하는 창이 뜸
    5. private을 객체에 위임한다면, **옮길 때, public으로 선택할 수 있음(안하면 default로 감)**
    6. **옮긴 후 (this.)getter()호출을 삭제하고 내부 필드context로 바꿈**

17. **enum은 **

    1. **`{}` : `값객체가 외부에서 파라미터로 입력`되면 `추상클래스로서 추상메서드를 이용해 [가상인자+람다식]에의해 수행되도록 전략객체로서 행위를 구현`해놓을 수 있지만, **

    2. **`()`: `분기별 값객체 반환`시  `분기문 자체를 값객체에 매핑`해놓고, `values()를 통해 매핑된 정보를 바탕으로 해당 값객체를 반환`해주는 `정적팩토리메서드`가 될 수 있다.**

       - 지연실행될 로직은 **`함수형 인터페이스(or전략인페.전메())으로 지연호출부 정의 -> 가상인자 람다식에서 외부구현(or전략객체로 생성)`의 방법이 있다**

       - 위임의 첫단추는 **`내부context로 사용하는 값은 위임객체에선 외부context`가 되도록 `위임객체context외에 모든 context값들을 변수로 만들어서 추출`**해야한다.
       - **특히 파라미터가 제일 많은 부분을 확인해야하며, `조건식 내에서 메서드호출된 것도 값이다!!`**

       - **boolean문안에서 `여러객체를 이용한 메서드호출() -> 1개의 응답값`을 가지는 `값(파라미터) 1개`로서 -> `1개의 외부context로 위임`될 수 있도록 `추출될 로직보다 더 위쪽에 미리 1개의 지역변수로 빼놔야한다.`**

         ![image-20220729143012230](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729143012230.png)

       - **badCase: `로직 위임전 [내부 여러객체.메서드호출()]부를 지역변수로 안빼놨을 때`**

         - 여러객체를 사용한 메서드호출은 어차피 1개의 값으로 사용되는데, 연관된 객체가 모두 변수로 뽑힌다.

         ![a5704e03-7a3a-430e-bc57-6de5e94fd817](https://raw.githubusercontent.com/is3js/screenshots/main/a5704e03-7a3a-430e-bc57-6de5e94fd817.gif)
         ![image-20220729143737025](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729143737025.png)

       - **GoodCase: `로직 위임전 [내부 여러객체.메서드호출()]부를 위임로직 더 위쪽에 지역변수 1개로 응답값을 받았을 때`**

         ![710eccce-c554-4ce1-8a24-ab02c97ae2ff](https://raw.githubusercontent.com/is3js/screenshots/main/710eccce-c554-4ce1-8a24-ab02c97ae2ff.gif)

         ![image-20220729143915942](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729143915942.png)

18. 캐싱 도입 방법

    - 정팩메가 필요하다
    - static컬렉션(hashMap) CACHE (기존 생성자파라미터를 key, 객체를value로 하는 Type)를 필드초기화하고
    - static정팩메에서 `if 없으면 생성해서 넣어주고 , 있으면 get`을 요청한다
      - computeIfAbsent(key, 가상인자람다식으로 value생성(객체생성))
    - **미리 static블럭에서 static변수들(캐싱변수도)을 초기화해서 캐싱할 객체들을 미리 생성할 수 있음.**
    - **여러쓰레드에서 입력할 가능성이 있다면, ConcurrentHashMap으로 캐싱static 변수를 초기화한다.**
    - **캐싱대상 객체는, 상수나 다름없기 때문에, 값으로 비교하기 위해 eq/hC 오버라이딩 한다.**

19. **일급컬렉션도 `빈컬렉션으로 시작하여 add시 새객체반환`하는 일급컬렉션으로 만든다.**

    - **new 빈컬렉션을 인자로 받는 `부생성자`를 만들어주면 된다.**

20. enum의 생성은 Enum.상수의 빨간줄로 만든다.

21. **`메소드 인자나 조건식에서 연산`을 하고 있다면 -> 메서드 추출로 리팩토링한다.**

22. functional자리에 supplier의 객체생성자 호출이면, 가상인자를 `ignored -> `로 만들고 안쓰면 된다.

23. **`여러종류의 객체를 응답받는 메서드의 응답값`중에 특정 class가 응답되어야하는 메서드의 테스트 코드 작성시**

    - return null으로 작성하고
    - **Object형으로 변수**로 받고
    - assert문에서 **isInstanceOf**( 특정클래스.class )로 확인한다.

    ![image-20220803172709657](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803172709657.png)

24. `enum에 값이 매핑되는 순간부터 바로 getter를 작성`해주면 된다.

25. **`일급컬렉션으로 포장안된 상태`라면, `동일형 객체 일괄처리`는 List.of()나 Stream.of()를 사용해서 묶어서 처리한다.**

    - **`동일형 객체끼리의 단순연산`도 List.of()로 묶은뒤 stream으로 연산이 가능하다.**
    - **단일형은 `단순연산도 일단 묶어서 처리`하자.**

26. **동일형을 묶어서 `일괄처리 or 단순연산`한다면, `묶는 부분(List.of)에서 일급컬렉션을 도입`하고, `일괄처리 + 단순연산되는 로직을 위임`하자.**

    - **`단순연산도 -> 묶어서 일괄처리`해놓으면 -> `add로 갯수가 늘거나 줄어도 메서드가 유지`된다**

27. **`있는지 없는지 유무`만`(몇개 있는지)filter + count`가 아니라`anyMatch( -> 개별요소판단 )`로 판단한다.**

28. **책임위임을 위임하려면, 일단 `현재context상의 메서드의 파라미터`에 걸려있어야 하며, `static메서드라면, static키워드를 삭제하고 위임`해야한다.**

    - 파라미터로 걸면서 getter를 사용했다면, 위임후에도 내수용getter를 쓰고 있으니 필드로 바꿔줘야한다.

29. **또다른 리팩토링으로서 `조건식에 하나의 도메인에 대한 메서드호출이 나열되어있다면, 메서드 추출시 파라미터에 1개만 도메인만 걸리며, 이 또한 묶어서 책임을 위임`할 수 있다.**

    - 역시 static안이라면, static을 지우고 위임한다.

      ![image-20220803234519904](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803234519904.png)

      ![image-20220803234556959](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803234556959.png)

30. **여러 종류의 객체return는 `추상체 응답의 상태패턴`을 고려한다.**

    ![image-20220804113722642](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804113722642.png)

    - **상태패턴을 도입했으면, `응답값으로 나온 현재 상태`를 바탕으로 `추상체에 있는 [다음상태로 갈 수 있는 인자]를 받아 [다음상태로가는 메서드]`를 정의해줘야한다. `에러호출로 종료되는 제일 쉬운 현재상태(blackjack)부터 다음 상태로 넘어가는[trigger메서드]를 만들자`**
    - **특정 쉬운 구현체부터 시작한다면, `테스트에서 다운캐스팅해서 구현체만의 메서드로 생성하여 테스트하고 공통이면 올린다`**

    - **특정구현체의 메서드이며, `thr 던질 메서드`라도 `미래에 공통으로 사용될 예비오퍼레이터라면, 응답형을 지정`한뒤 던진다.**
      - 게임종료는 thr로 한다.
    - **`다운캐스팅된 구상체만의 메서드 개발이 완료`되면, `다이어그램을 확인해서, 올려도 되는지 판단`한다.**
      - **다이어그램 + CompareFile을 펼쳐 모든 구상체가 호출해도 되는 메서드인지 확인**한다.
        - 다이어그램 단축키 : ctrl + alt + shift + U
        - compareFile : 구상체들만 선택후 ctrl + D
      - 올릴 거면, 다운캐스팅했떤 로직을 삭제하고, 다른 구상체들도 구현한다.
    - **trigger + 정보를 판단하려면, `상태객체는 생성시부터 이미 정보를 상태값으로 가지고 있도록`해야한다.**

31. **일급컬렉션에서 add할 때, 내부에서 새로운 일급컬렉션 상태를 만들기 위해, `업데이트된 컬렉션 필드 상태`를 만들어야하는데,  `기존상태값(컬렉션 필드)를 얕은복사해서 연관성을 [떼어낸 상태에서 업데이트]야한다`**

    - **기존 컬렉션 필드는 add하기 전에 얕은복사한 뒤, `생성자복사된 새 컬렉션에 add`**하고, 그것을 새로운 상태로 삼는다.

32. **상태필드가 `객체이상, 일급컬렉션`인 경우, `현재 구체적인 값의 상태를 물어볼땐  getter대신 메세지`를 보낸다**

33. **setNext, add와 같이 `같은형 객체`를 반환하는 메서드들뿐만 아니라 `같은카테고리인 추상체`를 반환하는 메서드들도 `체이닝 메서드`이다.**

34. 작은 코드라도 수정한다면, 복사해서 수정하고 지운다.

35. **`인자입력시 List.of() -> 가변배열`로 입력하도록 변경한다.**

36. **`상태변화가 없는 객체`는 `캐싱객체` or` VO로, eq/hC를 재정의하여 값이 같으면 같은 객체가 되는 값객체`라서 `testutil패키지 > Fixtures클래스`안에 `상수객체`로 만들어 써도 된다.**

    - test루트에서 **`testutil` 패키지를 만들고 내부 `Fixtures`를** 만들자
    - **Fixtures에 쓸려면 `어디서든 불러도 같은 객체가 생성되도록 캐싱==싱글톤이 보장`되어야한다.**
      - **`캐싱객체`는 test코드에서 `isSameAs`**로 **확인**해야한다.
      - **`eq/hC의 VO값객체`는 test코드에서 `eq/hC 재정의 후 isEqualTo`로 확인해야한다**

    ![image-20220805123413269](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805123413269.png)

    - **상수객체 Fixture는 `한글로 알아보기 쉽게 네이밍`해도 된다.**

    - 상수 추출후, **`Fixture로 옮기기전`에 미리 다 상수로 바꿔놓으면 더 쉽다(`ctrl + H`)**

      - **`Fixture로 옮긴 후 프로젝트 전역으로 확인하고 싶다`면, `상수값(우항)을 Ctrl + shift + F`로 찾으면 된다.**

        ![image-20220805130338811](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805130338811.png)

    

37. **`시작객체`가 있다면, `같은 패키지로 몰아서 시작객체public을 제외한 나머지들은 default가시성`을 가지도록 해서, 외부에서 생성안되도록 한다면, `사전검증을 안해도 된다.`**
    - **만약, 생성자가 기본생성자라서 정의를 안해줬다면, `재정의 후 가시성변경`해줘야한다.**
      - **대표적인 예가 `유틸클래스(상태값없이 input->output static메서드만 가지는)는 생성자를 private가시성으로 변경`해주는 것이다.**
      - **생성자가 없는 클래스 -> `public으로 열여있으며, 재료를 바탕으로 상태값을 만들지 않는 클래스`**
    - client가 잘못입력할 수도 있기 때문에 **`중간에 생성되는 상태객체들`은 `외부에서 생성못하도록 가시성으로 막아주기`**

38. **`특정 구상체에만 사용되는 메서드`가 등장했다.**
    - 상속관계면, 조합관계로 바꾼다.
    - **이미 인터페이스를 구현하는 조합관계의 구상체였다면**
      1. **일단 인터페이스에 올리고, 특정구상체만 자기메서드를 로직을 구현한다.**
      2. **마저 테스트를 진행해야하니 `나머지 구상체들은 로직 없이 구현만 해준다`**
      3. **이후 `해당메서드 테스트가 끝`나면, `특정구상체 외 사용하지 않는 구상체들에게 내리기 전`에 `중간에서 막아주는 추상클래스로 만들어지는 중간추상층`을 만들어준다.**
         - **`억지로 구현한 코드가 중복`이라서 중간추상층을 두고 중복코드를 제거한다는 개념으로 간다.**
    - **추상클래스 도입시 `중간에 먹어줘서 공짜로 물려주는 것들은 (템플릿메소드) final을 달아서 자식이 수정 못하게 막아야한다`**
      - **추상클래스의 public 템플릿메서드라면 `눈꽃모양으로 final`을 확인하자.**

39. **좋은 부모를 만들기 위한 전략(템플릿메소드) 중 `중복 필드 처리`**

    1. [Object)중복로직제거 Strategy to Templatemethod(Plan3)](https://blog.chojaeseong.com/java/%EC%9A%B0%ED%85%8C%EC%BD%94/oop/object/strategy/templatemethod/plan/connectobject/2022/07/12/Object_strategy_to_template_for_duplicate.html#next%ED%95%84%EB%93%9C%EB%A1%9C-%EC%97%B0%EA%B2%B0%EB%90%98%EB%8A%94-%ED%95%A9%EC%84%B1%EA%B0%9D%EC%B2%B4%EC%9A%A9-%EC%83%9D%EC%84%B1%EC%9E%90-%EC%A3%BC%EC%9E%85-%EB%8C%80%EC%8B%A0-void-setter%EB%A5%BC-%EA%B0%80%EC%A7%84-%EC%B6%94%EC%83%81%ED%81%B4%EB%9E%98%EC%8A%A4%EC%97%90-%EA%B5%AC%EC%83%81%EC%B8%B5-%EA%B0%9D%EC%B2%B4%EB%B3%84-%EC%B2%B4%EC%9D%B4%EB%8B%9D-%EB%B0%9B%EA%B8%B0%EA%B8%B0%EB%8A%A5%EC%9C%BC%EB%A1%9C-%EB%8C%80%EC%B2%B4%ED%95%98%EA%B8%B0)
       1. **추상클래스는 좋은 상속의 부모로서 `변수는 private`, `메서드는 final or protected abstract`, `생성자없이 setter로`**확인하여 달아준다.
    2. **`구상체별 중복되는 필드`를 올릴 땐, 구상클래스 `생성자 주입` -> `setter메서드 -> private필드`로 올린다.**
    3. **부모의  private필드는 부모내에서 처리하게 하고, `자식은 부모private필드자체는 못쓰고, final 물려주는 템플릿메소드들만 쓴다.`**
       - 자식들이 훅메서드 구현시 필요한 정보들은, 자기가 생성자 주입받아서 쓰면 된다.
       - **자식이 부모의 private필드값을 써야한다면, `부모에게 구현된 getter를 물려받아 쓰면`된다.**

40. **암기getter**

    1. **`getter정의시 return 불변객체`라면, `public열어두기 가능 + dto없어도 됨`** 
       - **불변의 일급컬렉션 객체 반환 -> dto없는 public getter로 제공**
       - **불변 객체 아니라면, `Dto로 만들어서 반환`**
    2. **`getter정의시 return 컬렉션필드라면, view로 보낼 땐, 깊은 복사로 못건들게 해서 반환`**
       - **불변의 일급컬렉션 `자신 내부의 컬렉션 필드 반환`**
         - server사이드 반환이라면, 얕은복사 반환후, 내부에서 객체들 조작
         - **view반환이라면,  `깊은 복사 반환 or DTO로 반환`후, 내부 객체들 조작안되게**

41. **`getter 추가 -> 중복필드로서 같이 움직이는 해당 상태값`이 추가된 상황에서**

    1. **`최상위 인터페이스에서부터 추가`되었으며**

    2. **구현시, `어느 구상체들도 thr로 예외없이 다 100% 다 구현`된 상태라면**

    3. **`구상체 100%구현 메서드 + 필드`로서 `중복제거를 위한 중간추상층`이 추가된다.**

       ![image-20220806171853571](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806171853571.png)

42. **상태객체의 인터페이스 `오퍼레이터는 최소 4개 + @가 필요`하다**

    1. trigger 메서드: draw()

    2. 중단 메서드:  stay()

    3. 현재상태값 반환메서드: getter cards()

    4. **`is끝난상태[중간추상층]인지` 확인 메서드: boolean isXXXX()**

       - **상태객체 소유 객체(Player)는 `상태가 isFinished될때까지 반복작업할 것`이다.**

       - **여기서는 stay개발시 불가하여 묶여진 `Finished 형용사 중간추상층`이 이미 있으니 `끝난 상태냐`라고 물어볼 수 있다.**
         - **`특정 구상체인지는 물어보지 않는다. 그놈빼고 다 false를 대답하기 때문에` -> `특정 구상체인지는 물어보지말고 메세지를 던진다.`**

       - **`다형성 인터페이스에 존재하는 boolean 메서드는 -> 구상체들이 구현만 해주면 알아서 개별 답변되는 메서드`다**

         ![image-20220806180149358](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806180149358.png)

43. **추상체변수의 `구상체별 기능 구현`은 `전략패턴처럼 구현만 하면 알아서 적용`된다.**

    - **이미 외부생성(전략패턴) or trigger메서드(상태패턴)에 의해**  State state안에는 Blackjack이나 Bust나 Stay가 담겨있는 상태다
      - **개별로 구현만 해주면, 알아서 작동한다.**
      - **대신, 알아서 계산안되어야할 놈들은 thr나 예외처리 해놔야한다.**

    - **추상체 변수의 `상태이용 개별구현 메서드`는 구상체를 물어보지말고 구현만 해놓으면 알아서 적용된다.**
    - **상태값이 일반 객체였으면... 물어보고 판단했을 것인데... 추상체변수인 순간 알아서 구현됨을 생각하자.**

44. **중간추상층이 좋은 부모가 되려면, `공통점이 아예 없는 개별구현로직 조차, 개별구현훅메서드를 래핑하고 있는 public final 템플릿메소드`로 가지고 있고, `서로 다른 로직은 protected abstract 훅메서드만 내보내줘야한다.`**

    1. CompareFile(ctrl+D)로 **구상체 메서드들의 공통점과 차이점**을 살펴보고, **`어느부분까지를 훅메서드로 뺄 것인지 생각`한다.**
       - **상수만 서로 달라도, 해당부분을 훅 메서드로 뺄 수 있다.**
    2. **구상체들 중 `서로 다른 훅 부분에  가장 파라미터가 많이 사용되는 로직`을 가진 구상체 1개를 선택하고 `해당 메서드를 중간추상층으로 잘라내서 올린다`**
    3. **올린 public 템플릿메서드 내부에서 `다른 부분만 내수용 메서드 추출(구상체private -> 추상체protected abstract)의 훅메서드로 만든다`**
       - **올리고 나서 훅을 만든다면, `메서드 추출후 가시성을 private -> protected abstract로 직접 변경`해줘야한다.**
       - **올린 템플릿메서드는 final로...**
    4. **나머지 구상체들은, `훅메서드를 구현`하면서, 기존 구현메서드의 다른 부분만 훅에 입력한 뒤, 기존 메서드는 삭제해준다.**

45. **다 구현된 상속구조에서 `추상화레벨을 맞추기 위한 중간추상층 도입`을 한다.**

46. **제한된 종류의 상태값을 가진다면 `not final 시작상태 필드초기화` + `setter대신 ->  public void toggle`로 정의해준다.**

47. **`if 상태확인 -> 상태업뎃`을 매번 확인하면서 반복하는 것이 `while 상태확인 -> 상태업뎃`이다.**

48. 좋은 부모를 위해 생성자없는 [not final + set계열 메서드]를 정의했다면, set전에 null나올 수 있으니, 빈값 필드초기화 해야한다

- 이미 정해진 종류의 setter는 state = state변화의 toggle에 가깝다
- 매번 상태확인후 시행하는 if condition -> action를 반복하는 것이 while condition 반복문이다.
    
    - 이 때, 반복되는 동안 메서드 인자인 재료는 iterator처럼 1개씩 주어질 수 있어야한다. 
- 도메인 객체를 함부러 public메서드의 파라미터 == 외부생성으로 주지말자.
    
- 내부생성 or 내부에서 1개씩 제공해주는 iter느낌의 객체를 받을 수 있다.
    
- deque의 재료는 미리 list로 만들어놔야한다. 제공할 때 생성자복사시 형변환을 같이해서 줄 수 있다.
    - deque로 변환해서 가지고 있으면, pop()으로 iterator객체가 될 수 있다.
	- 형변환되어 제공될 재료 컬렉션은, 미리 만들어놓아도 된다면, static필드 + static블럭으로 초기화해놓는다.
	
- 가변 변수 콜렉션에 add는 stream으로 해결할 수 있다. 
    
- 2중 for문은 stream을  stream->map->stream->map -> 첫번째 map을 flatmap으로 만들면 사용할 수 있다.
    
- 생성자내에서 [내부context인 상태값(가변변수) 재할당]을 포함한 메서드추출은 파라미터없이 그대로 재할당을 들고들어가 only내수용이 된다.(위임불가)
    - 재할당되는 line에 지역변수를 만들고, 그 지역변수를 재할당시키게 한 뒤, 내부context재할당을 제외한 로직을 추출하여, [재할당되는 값return]이 추출되게 한다.
	- 만약, 내부context도 같이가야한다면, 내부에 포함시키고, 파라미터 추출을 해준다.

	





# 전략패턴/enum 함수형 인터페이스



## 랜덤을 셔플로 구현하여 데이터를 제공해주는 Deck에서, 랜덤부분을 전략패턴으로 만들고, 외부에서 [직접 반환되는 객체를 만들어놓고 -> 최종 객체만 람다식에 넣어] 제공함.

### (3) 블랙잭

1. 컴퓨터내부에서 로직의 시작인 card 2개가 주어진다면, **정제된input이라 생각하고 카드부터 만든다.**

   - **제한된 종류의 값 = 상수묶음은 enum으로 만들어서 카드를 만든다.**
   - 테스트메서드명은 인자 -> 1case 가 결정한다.

   ![image-20220803141223263](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803141223263.png)

2. **4 x 13 종류의 객체는 캐싱이 가능하다.**

   - 캐싱을 적용하려면 일단 **생성자 인자 그대로 `정팩메`로 만들고, 기본생성자는 private해준다**

   ![eb9d9669-3c47-4bc2-a036-e045e7bae549](https://raw.githubusercontent.com/is3js/screenshots/main/eb9d9669-3c47-4bc2-a036-e045e7bae549.gif)

   ![image-20220803141533284](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803141533284.png)

3. **원래는 `기본생성자의 1개 인자`에 대해 hashMap`<생성자인자, 객체>`형으로 static CACHE map을 만들어야하지만, `파라미터가 2개일경우, 1개로 합친 Key`를 만들어야한다.**

   - 2개이상의 정보를 묶은 class를 만들어도 되지만, **string을 이용해서 합쳐 1개의 key로 만들어 `<String, 객체>`형을 많이 사용한다.**
   - **캐쉬의 기본공식은 꺼내서 없으면 put해주고, return get을 반환이다.**

   ![303bdea4-3f7f-401f-8ced-e537eb227a77](https://raw.githubusercontent.com/is3js/screenshots/main/303bdea4-3f7f-401f-8ced-e537eb227a77.gif)

   ![image-20220803142249589](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803142249589.png)

4. **테스트메서드명도 함수를 from or of의 정펙메로 바꿔주고, isSameAs로 테스트한다.**

   ![image-20220803142749590](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803142749590.png)

5. **java의 기능으로서 없으면 key에 대한 value를 생성해주고 있으면 반환하는 `computeIfAbsent`를 사용해서 리팩토링한다.**

   - 또한 캐싱될 객체의 수를 알고 있으면 미리 넣어줘도 된다.

   ![e488a62d-24bf-4ab9-8f50-1dc5ed300d1a](https://raw.githubusercontent.com/is3js/screenshots/main/e488a62d-24bf-4ab9-8f50-1dc5ed300d1a.gif)

   ![image-20220803143606805](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803143606805.png)

6. CACHE대상객체는, 거의 값객체기 때문에,  **값으로 비교하기 위해 eq/hC오버라이딩도 해준다.**

7. **이제 `가진 2장의 카드`로 `정해진 종류의 행위마다 변화하는 상태 판단`하면서, `Game`을 풀어 가야한다.**

   - 카드2장 -> if 상태판단 -> **`상태에 따른 행위 -> 다른 상태`**
   - **`if 상태에 따른 행위의 결과가 다른 상태가 나온다면, 상태패턴`을 고려한다**
   - 2장의 받은 상태에서
     - if 21 -> 블랙잭 상태 -> stop
     - if 21미만 -> hit상태 -> stay or hit지속
   - **`가장 만만하게 응답될 상태의 input`부터 case를 만들고 -> 테스트메서드명으로 시작하면 된다.**
     - **`hit가 output상태로 나오는 input을 넣어주자.`**

8. Service가 아닌 만들어낸 input부터 시작하는 Game클래스를 만들고, **서비스메서드처럼 static으로 start()를 만들고 재료를 넣어주자.**
   ![image-20220803172330589](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803172330589.png)

   - **input으로 `hit대상이되는 카드2장을 인자`를 넣어주고, `응닶값이 hit상태`이어야한다.**

   ![c67370cb-5be1-4ac1-8c1a-ad972ed07cfc](https://raw.githubusercontent.com/is3js/screenshots/main/c67370cb-5be1-4ac1-8c1a-ad972ed07cfc.gif)

   ![image-20220803172709657](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803172709657.png)

   ![image-20220803173533986](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803173533986.png)

9. **다음은 Game.start()가 blackjack상태를 응답하도록 작성해보자.**

   - 테스트에 인자를 blackjack으로 넣어주고

     ![image-20220803174548392](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803174548392.png)

   - **blackjack시 ACE가 11로 사용될줄알아야한다. enum에 매핑된 값이 필요함을 인지한다.**

     ![image-20220803174605022](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803174605022.png)

   - **`enum에 값이 매핑되는 순간부터 바로 getter를 작성`해주면 된다.**

     - **ACE는 1or11을 가질 수 있으나 `규칙상 상한이 21점으로 제한되어있다면, 11을default시 2장을 아예 가질 수 없기 때문에, default를 작은 수로 주고 보정`한다**

       ![image-20220803175357571](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803175357571.png)

     ![image-20220803174635793](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803174635793.png)

   - card입장에서는 enum getter -> enum point getter 2번을 거쳐야하므로, getter를 래핑해서 만들어준다.

     ![image-20220803180048429](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803180048429.png)

     ![image-20220803180056251](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803180056251.png)

10. **2장의 카드 중에, `ACE가 포함되어 있을 경우` && `[11로 쓸 때, 합 21] -> [1로 쓰면 합 11]`일 경우만, blackjack이 되도록 처리해줘야한다.**

    - **2개의 카드 중에 ace가 있는지 확인**한다.

      - **일급컬렉션이 아닌 상태에서 `동일형 객체 일괄처리`는 List.of()나 Stream.of()를 사용해서 묶어서 처리한다.**

      - **`있는지 없는지 판단`은` filter + count`가 아니라`anyMatch( -> 개별요소판단 )`로 판단한다.**

        ![98305185-6d82-4ef3-9790-2d8f12751669](https://raw.githubusercontent.com/is3js/screenshots/main/98305185-6d82-4ef3-9790-2d8f12751669.gif)

        ![image-20220803223426698](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803223426698.png)

    - **ace를 가졌다면 &&** 2장의 **합**이 11이하인지 확인한다

      - 11이하이면, ace는 10으로 쓰일 수 있다.
      - **11이면,  ace를 가진 경우 blackjack이다.**

      ![image-20220803223757989](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803223757989.png)

    - **즉, ace를 작은값로 유지하고, 기준값을 큰값일때로 잡아서 처리하면 된다.**

      - **ace(1) + 10 -> 11이면 21로 블랙잭**
      - ace(1) + x -> 11미만이면, ace(11) + x
      - ace(1) + 11 -> 12이상이면, 교환불가
      - **ace가 2장있다면? 변환될 수 있는 ace는 한장이다. 2장 변환시 이미 22가 되어버림**

      ![image-20220803224512177](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803224512177.png)

    

    

11. 테스트를 통과했다면, **리팩토링한다**.

    1. **일급컬렉션이 아닌 `같은형 객체 일괄처리`를 위해 List.of()로 묶었지만, `같은형끼리의 단순집계`또한 List.of()로 묶어서 누적연산으로 처리가능하다.**

       - 같은 context에서 `일괄처리`와 `같은형 끼리의 연산`이 뭉쳐있다면, **List.of()나 Stream.of()로 묶어주는 것은 공통코드가 된다. `파라미터 추출로 각각을 빼낸뒤, 1개의 변수로 대체`할 수 있다.**

       ![c99a733b-5da6-459c-afda-cf65c89c814e](https://raw.githubusercontent.com/is3js/screenshots/main/c99a733b-5da6-459c-afda-cf65c89c814e.gif)

       ![image-20220803225702034](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803225702034.png)

    2. **같은형끼리의 `단순연산` or `일괄처리`로 인해 `2개이상의 같은형을 List.of`로 묶었다면, `List.of()로 묶는 곳에서 일급컬렉션`을 고려한다.**

       - Stream.of()는 List.of()로 바꿔서 일급컬렉션의 생성자 인자로 올리자.

       - 이후 **`인자없는 생성장에서 빈컬렉션으로 초기화`하여, add가능한 일급컬렉션으로 수정하자.**

         ![image-20220803230332195](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803230332195.png)

         ![bd76e09c-c932-4314-8299-2d915b9bc787](https://raw.githubusercontent.com/is3js/screenshots/main/bd76e09c-c932-4314-8299-2d915b9bc787.gif)

         ![image-20220803230845620](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803230845620.png)

       - **인자없는 생성자는 `부생성자`이므로 `빈컬렉션 초기화를 this()`에 넣어서 초기화해주자**

         - add시 새객체 반환시, 얕은복사 안해줘도 add전 객체는 버려져서 오염될 일이 없을 것 같아서 빼줬다.

         ![1733ea67-d615-4ce6-9ac7-aafe4801d9a3](https://raw.githubusercontent.com/is3js/screenshots/main/1733ea67-d615-4ce6-9ac7-aafe4801d9a3.gif)

         ![image-20220803231130552](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803231130552.png)

12. **책임위임을 위임하려면, 일단 `현재context상의 메서드의 파라미터`에 걸려있어야 하며, `static메서드라면, static키워드를 삭제하고 위임`해야한다.**

    - **getter를 쓰더라도 파라미터로 들어가서 완성**해놓자.
    - **static메서드라면, `static키워드를 지운 뒤 f6으로 위임`해야한다**
    - getter를 썼다면, **위임된 객체에서는 내수용getter가 사용되고 있으니 지우고, 필드로 바꾸자.**

    ![ac7f2bd5-ca83-4487-bcd0-c1065dc82c97](https://raw.githubusercontent.com/is3js/screenshots/main/ac7f2bd5-ca83-4487-bcd0-c1065dc82c97.gif)

    ![image-20220803234008203](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803234008203.png)

13. **또다른 리팩토링으로서 `조건식에 하나의 도메인에 대한 메서드호출이 나열되어있다면, 메서드 추출시 파라미터에 1개만 도메인만 걸리며, 이 또한 묶어서 책임을 위임`할 수 있다.**

    - 역시 static안이라면, static을 지우고 위임한다.

      ![image-20220803234519904](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803234519904.png)

      ![image-20220803234556959](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220803234556959.png)

14. 상태패턴 적용을 위해 **state라는 추상체 인터페이스를** 만들고, **응답형을 Object가 아닌 추상체로 주면 -> `알아서 응답되는 객체들을 구현`하라고 intellij가 알려준다.**

    ![280a5884-db1a-4fb6-8d73-50c00726bcde](https://raw.githubusercontent.com/is3js/screenshots/main/280a5884-db1a-4fb6-8d73-50c00726bcde.gif)

    ![image-20220804114048025](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804114048025.png)

15. 테스트코드의 응답형에 따른 변수형도 변경해준다.
    ![image-20220804114302886](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804114302886.png)

16. **추상체를 만드는 순간부터 `패키지를 분리하여, 패키지폴더 대상 다이어그램으로 의존성을 확인`하자.**

    - **이후, `구상체만의 메서드 개발후 올리기전 다이어그램 + CompareFile하자`**

    ![image-20220804114547248](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804114547248.png)

17. **상태패턴을 도입했으면, `응답값으로 나온 현재 상태`를 바탕으로 `추상체에 있는 [다음상태로 갈 수 있는 인자]를 받아 [다음상태로가는 메서드]`를 정의해줘야한다. `에러호출로 종료되는 제일 쉬운 현재상태(blackjack)부터 [다음 상태로 넘어가는 메서드]를 만들자`**

    - 테스트메서드에 작성한 `응답된 상태`
      - Hit
      - Blackjack -> **draw즉시 `에러 호출로 종료`라서 더 쉽다.**

    ![image-20220804115950502](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804115950502.png)

18. **이 때, `특정 쉬운 구현체부터 메서드 작성`이라면, `공통메서드일지 모르니, 오퍼레이터로 만들지말고 다운캐스팅해서, 구현체만의 메서드`로 만든 뒤, `공통이면, @Override`해서 올린다.**

    - **응답값을 받을 때 (특정구현체) 다운캐스팅**을 하고, 메서드를 작성한다

    ![7b4e31c2-60ee-4cd3-976e-aedf73a30026](https://raw.githubusercontent.com/is3js/screenshots/main/7b4e31c2-60ee-4cd3-976e-aedf73a30026.gif)

    ![image-20220804120914397](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804120914397.png)

19. **특정구현체의 메서드이며, `thr 던질 메서드`라도 `미래에 공통으로 사용될 예비오퍼레이터라면, 응답형을 지정`한뒤 던진다.**

    - 게임종료는 thr로 한다.

    ![image-20220804121345849](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804121345849.png)

20. **`다운캐스팅된 구상체만의 메서드 개발이 완료`되면, `다이어그램을 확인해서, 올려도 되는지 판단`한다.**

    - **다이어그램 + CompareFile을 펼쳐 모든 구상체가 호출해도 되는 메서드인지 확인**한다.
      - 다이어그램 단축키 : ctrl + alt + shift + U
      - compareFile : 구상체들만 선택후 ctrl + D
    - 올릴 거면, 다운캐스팅했떤 로직을 삭제하고, 다른 구상체들도 구현한다.

    ![5f9ff154-0934-4173-a368-295ad75eadba](https://raw.githubusercontent.com/is3js/screenshots/main/5f9ff154-0934-4173-a368-295ad75eadba.gif)

    

21. **Override + pull members up으로 오퍼레이터로 올린 뒤, `테스트상 다운캐스팅한 로직은 삭제`해준다**

    ![ae3803d6-7046-4488-a724-eb9ba30ff1bc](https://raw.githubusercontent.com/is3js/screenshots/main/ae3803d6-7046-4488-a724-eb9ba30ff1bc.gif)

    ![image-20220804125036585](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804125036585.png)



21. **blackjack -> 게임종료(thr)는 끝났다. 이제 `hit상태에서 -> draw or stay`를 할 수 있다.**
    - **일단 draw부터 만든다.**
    - **hit상태에서는**
      1. **합 20이하라면, hit상태**
      2. **합21이라면, blackjack상태**
      3. **합 21초과라면, bust상태이다**
    - **`테스트에서는 case마다 인자로 넣어줘서 차근차근 개발하며, 제일 쉬운 것(hit)`부터 응답하게 만들어나가면 된다.**

22. **hit응답상태에서 `합 20이하가 되는 인자`를 넣어줘 `다시 히트상태를 응답`하도록 case를 만든다.**

    - **첫 case만 만들 땐, 빠르게 return이라 연산식이 없다. 하지만,,,**

    ![32a9fc9b-72a3-4f55-b1f6-bf7d929e9ae8](https://raw.githubusercontent.com/is3js/screenshots/main/32a9fc9b-72a3-4f55-b1f6-bf7d929e9ae8.gif)

    ![image-20220804125940467](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804125940467.png)

    - 아직까지는 hit만 빠르게 반환하도록 만든다.

      ![image-20220804130150609](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804130150609.png)

      ![image-20220804125843507](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804125843507.png)

23. **이제 hit에서 2번째 쉬운 상태응답case인, hit -> bust로 간다.**

    - 테스트를 작성하고

    ![image-20220804131116751](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804131116751.png)

    - **Bust 클래스를 만들고, State구현한 뒤, `hit의 draw()의 내부 로직을 작성해야하는데..`**

      ![image-20220804131254370](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804131254370.png)

    - **Hit의 트리거메서드인 draw의 2번째 case Bust를 판단하려면, `기존 카드정보를 모두 상태값으로 쥐고 있고 vs 메서드 인자로 넘어온 정보를 통해 상태업데이트 후 새객체반환`해야한다.**

      ![image-20220804131617383](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804131617383.png)



24. **최초의 상태객체를 만들어내는 `Game.start()`에서 `현재정보를 생성자의 인자로 넘겨줘 상태값으로 가지고 있어야`한다.**

    ![image-20220804131920221](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804131920221.png)

    - **trigger + 정보를 판단하려면, `상태객체는 생성시부터 이미 정보를 상태값으로 가지고 있도록`해야한다.**

    - **최초로 상태객체가 만들어지는 곳**에서 **`현재 상태를 결정하는 정보를 생성자  주입해서 물고 있자.`**

      ![d3c82900-8efc-4652-885c-f1b6844206a3](https://raw.githubusercontent.com/is3js/screenshots/main/d3c82900-8efc-4652-885c-f1b6844206a3.gif)

      ![image-20220804132506391](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804132506391.png)

    - **기존에 생성자 없이 사용하던 객체에, `상태값이 추가되어 생성자가 추가될 경우`, `부생성자로 빈값할당`으로 초기화해주는 `기본생성자`를 추가해서 `기존코드가 망가지게 않게 한다`**

      ![45c77192-814c-4c49-9d5e-a763f5ca0adb](https://raw.githubusercontent.com/is3js/screenshots/main/45c77192-814c-4c49-9d5e-a763f5ca0adb.gif)

      ![image-20220804132909980](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804132909980.png)

      ![image-20220804132919754](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804132919754.png)

      ![image-20220804133122081](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804133122081.png)

    - **또한, `trigger메서드내에서는 업데이트된 상태값으로 새 상태객체를 생성해서 반환`해줘야한다.**

      - 상태값도 포장된 일급컬렉션이라, cards + card를 처리해야한다.

      ![image-20220804132354817](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804132354817.png)

25. **같은형의 일급vs단일라면 메세지를 보내서 처리해야한다. `주로 add가 쓰일 것이다.`**

    - add를 만든다면, list + add로 인해 **상태변화된 불변일급컬렉션을 반환**해줘야한다.
    - **이 때, `기존 상태값(컬렉션 필드)를 얕은복사`후 add해야한다.**
    - **컬렉션 파라미터의 사전검증**으로서 **일급컬렉션에서 add하여 `새로운 컬렉션 상태`를 만들어, `새로운 일급컬렉션 객체`를 불변하게 반환할 때는, `기존상태값을 얕은복사해서 연관성을 떼어내야한다`**

    ![679b4781-0333-4a55-8484-3cef333d5a02](https://raw.githubusercontent.com/is3js/screenshots/main/679b4781-0333-4a55-8484-3cef333d5a02.gif)

    ![image-20220804154848612](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804154848612.png)

26. **상태필드가 `객체이상, 일급컬렉션인 경우`, `현재 구체적인 값의 상태를 물어볼땐  getter대신 메세지`를 보낸다**

    - **상태필드가 `객체이상, 일급컬렉션인 경우`, `현재 구체적인 값의 상태를 물어볼땐  getter대신 메세지`를 보내서 `값으로만 응답`받는다.**
      - **이미 가진 카드들의 합을 `묶어서 연산`하도록 프로그래밍 되어있으니, `갯수가 늘어나도 합 연산은 그대로 유지되고, 그 메서드를 내수용으로 사용`하면 된다.**

    ![image-20220804155811937](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804155811937.png)

    - 상태값이 객체이상이라면, 메서드를 보내서 현재상태를 물어본다

      ![2659722a-0b81-42b2-baf2-d05d89a2f64e](https://raw.githubusercontent.com/is3js/screenshots/main/2659722a-0b81-42b2-baf2-d05d89a2f64e.gif)

      ![image-20220804160524197](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804160524197.png)

27. 현재 진척도

    - hit or blackjack
      - **blackjack** -> 뽑을 시 예외발생해서 종료
      - hit
        - hit
        - **bust -> 뽑을 시 예외발생해서 종료** « 먼저 처리해주자
        - blackjack?

28. **`종료상태`객체는 `trigger메서드 호출시 예외발생으로 종료`까지 마무리해줘야한다.**

    - Blackjack상태객체, Bust상태객체 (앞으로 Stay도 종료상태일 것임)

    ![image-20220804161343735](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804161343735.png)

    - **참조변수 재할당시 람다캡처리 문제가 발생한다. 체이닝가능한 메서드는 체이닝해주자.**

      ![image-20220804161817136](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804161817136.png)

      ![image-20220804161614014](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804161614014.png)

29. **setNext, add와 같이 `같은형 객체`를 반환하는 메서드들뿐만 아니라 `같은카테고리인 추상체`를 반환하는 메서드들도 `체이닝 메서드`이다.**

    - 객체반환메서드는 체이닝을 생각하자.
    - **이 때, static메서드로서 게임출발을 담당했던 메서드도 `State`를 반환하는 메서드이므로 `체이닝 가능`하다**

    ![image-20220804162044271](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804162044271.png)



30. **2장인데, ace를 안 가진상태에서도 블랙잭이 될 수 있다. hit -> blackjack을 개발해보자.**

    - hit or blackjack
      - blackjack -> 뽑을 시 예외발생해서 종료
      - hit
        - hit
        - bust -> 뽑을 시 예외발생해서 종료
        - **blackjack -> ace를 가졋다면 합 11 , `ace가 없다면 합21`시 만족**

    ![image-20220804163356345](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804163356345.png)

    ![image-20220804163338000](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804163338000.png)

    ![image-20220804221126471](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804221126471.png)

31. **핵심로직을 시작하는 `카드2장input -> 상태객체 output`하던 스태틱 클래스도 `Ready 상태`의 상태객체다.**

    1. Game -> Ready 수정 

       - 테스트의 Ready.start() -> **찾아바꾸기로 new Ready()**.start()로 수정하자.

         ![e17fed9b-1609-49f1-a76d-868c057590ee](https://raw.githubusercontent.com/is3js/screenshots/main/e17fed9b-1609-49f1-a76d-868c057590ee.gif)

       

       ![image-20220804221313986](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804221313986.png)

    2. **State인터페이스 구현** 후 **start보다 `구현메서드draw()를 가장 위로`**

       - 상태를 구현한 구현객체라는 의도를 명확히 하기 위해서는 **`구상메서드를 가장 위로` 올린다.**

       ![image-20220804221725083](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804221725083.png)

       - **상태객체 패키지로 이동시킨다.**

         ![image-20220804222040550](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804222040550.png)

32. **`서비스 메서드 == 스태틱 메서드 like 유틸메서드`에서 -> `객체`가 되었다면**

    - 상태값으로 카드 2장을 받는다.
    - **상태값을 만드는 input들은 다 생성자로 들어와야한다.**
      - **메서드로 상태값 정보가 들어온다면, setter다. 지양해야한다.**

    - **서비스의 `유틸메서드 input` -> `생성자로 주입` 된다**

    - **서비스의  `유틸메서드내 로직을 거친 뒤 1개의 output응답값(상태객체)` -> `필드`로 가진다.**

      ![image-20220804223725402](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804223725402.png)

33. **start의 카드2장의 input은 `생성자로 주입`되고, 1개의 output인 상태객체는 필드가 되어 `상태객체를 초기화`해서 최초로 가지는 `상태 포장객체`가 Ready가 된다.**

    - **즉, 서비스메서드는 나중에 `첫 상태객체를 초기화해서 사용하는 포장객체`가 되는 것이다.**

    ![5272c2d7-0faf-41ed-bfc5-3f201631f616](https://raw.githubusercontent.com/is3js/screenshots/main/5272c2d7-0faf-41ed-bfc5-3f201631f616.gif)

    ![image-20220804224629295](https://raw.githubusercontent.com/is3js/screenshot
    s/main/image-20220804224629295.png)

34. **이렇게 `Cards`가 아닌 `다른 상태객체`를 가지게 되면, `상태객체가 아니라 상태객체를 사용하는 객체`가 된다.**

    - **하지만, `Ready`라는 상태객체도 `상태를 사용할 객체가 State state의 초기값으로 가져야할` 객체로서  엄연히 존재해야하며 `다른 상태객체들처럼 draw()를 구현`하여 `다른 상태로 넘어가야 한다`**

35. 정리하면

    1. Ready도 **다른 상태객체들과 `동일 상태값 필드`(Cards)**를 가지며 **`생성자로는 아무것도 주입 안된 빈카드로 초기화`하는 `기본 생성자`로 만들어져야한다.**

    2. Ready도 .start()로 다른 객체로 넘어가는 것이 아닌 **다른 상태객체들의 메서드처럼 `Trigger메서드(draw())`로부터 setter정보를 받아 `상태값업데이트 이후 상태값을 가지고 판단하여 다른 상태로 넘어가`도록 수정한다.** 
       - **Ready는 카드를 2장을 받는다. -> 다른상태객체처럼 draw로 1장씩 받도록 정의하고 `외부에서 2번을 호출`하던지 `다른 파라미터로서 받도록 오버로딩메서드`로 정의해준다.**

36. 빈생성자 -> 빈 상태값으로 생서될 수 있도록 **생성자를 수정**하고, **다음 상태객체로 넘어가는 로직을 draw로 옮긴**다. 

    ![8821434b-59bf-44fa-857c-0bf99997e8f8](https://raw.githubusercontent.com/is3js/screenshots/main/8821434b-59bf-44fa-857c-0bf99997e8f8.gif)

    ![image-20220804230734646](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804230734646.png)

37. **문제는 `Ready는 1장이 아니라 2장을 다 받은 상태에서 판단이 이루어져야하는데, 1장만 받고 hit로 바로 가고 있다.`**

    ![image-20220804230943424](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804230943424.png)

38. **Ready가 2장을 받은 상탠지 확인하는 것은 `Ready의 상태값인 cards`로 판단해야한다.**

    - 객체이상이므로 메세지를 던져 물어본다. **너 2장 받았냐고, 아직 2장미만의 Ready상태냐고**
    - **1장 받고 상태값이 바뀌면, 새객체를 반환해야줘야하므로 다시 new Ready(바뀐cards)로 응답한다.**
      - cards로도 만들어져야하므로 생성자가 추가되고, 기존 생성자는 부생성자가 된다.

    ![81ab59df-4c25-4618-99ca-509c250ba0e](https://raw.githubusercontent.com/is3js/screenshots/main/81ab59df-4c25-4618-99ca-509c250ba0e.gif)

    ![image-20220804233720485](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804233720485.png)

    - **밑에 로직도 업데이트된 상태값으로 생성하도록 수정한다.**

      ![image-20220804234229117](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804234229117.png)

      

39. **test에선 다 start(카드, 2장)으로 작성된 상태다.**

    - **`코드 수정은 기존코드start를 복사해서 수정draw`하고 나서 지운다.**

    ![648a43fa-9915-4db6-8955-27b213f1bc8e](https://raw.githubusercontent.com/is3js/screenshots/main/648a43fa-9915-4db6-8955-27b213f1bc8e.gif)

    ![image-20220804235357245](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220804235357245.png)

    - **서비스 or 핵심로직  통합테스트가 Ready라는 1개 상태객체 테스트로 바꼈다.**

      - **`개별 상태객체에 해당하는 코드들은 각자의 테스트로` 나누어서 옮긴다.**

      - **비록 Ready에서 시작하지만 일단 옮겨간다.**

      ![647523ba-25d4-4853-9ece-f32ec5ff414e](https://raw.githubusercontent.com/is3js/screenshots/main/647523ba-25d4-4853-9ece-f32ec5ff414e.gif)

40. **Ready부터 시작하는 테스트를, 해당객체부터 시작하도록 수정한다.**

    - **Ready는 인자없는 생성자에서 draw()2번했지만**
    - **`특정상태 객체는 cards를 상태값으로 주고 시작`하도록 변경하면 된다.**

    ![image-20220805002505575](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805002505575.png)

41. **파라미터 변경한 생성자 추가해주기 -> `파라미터 추가는 해당 파라미터로 생성자 추가한 뒤 오버로딩으로 처리`한다고 했다.**

    - client가 편해야한다.

    - Cards를 만드려면 List.of() 로 묵어줘야하니, **내부에서 묶어주고 가변인자로 주도록 변경해보자.**

      - 기존
        ![image-20220805002739625](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805002739625.png)

      - **`인자입력시 List.of() -> 가변배열`로 입력하도록 변경한다.**

        ![172a6aed-0912-45a7-a0d6-ff892d0afe2f](https://raw.githubusercontent.com/is3js/screenshots/main/172a6aed-0912-45a7-a0d6-ff892d0afe2f.gif)

        ![image-20220805003122391](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805003122391.png)

        ![image-20220805003145189](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805003145189.png)

      

42. **`상태변화가 없는 캐싱 객체`는 싱글톤이라, `testutil패키지 > Fixtures클래스`안에 `상수객체`로 만들어 써도 된다.**

    - test루트에서 `testutil` 패키지를 만들고 내부 `Fixtures`를 만들자

      ![bffe3520-7e82-4591-8991-e8338e197072](https://raw.githubusercontent.com/is3js/screenshots/main/bffe3520-7e82-4591-8991-e8338e197072.gif)

      ![image-20220805123413269](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805123413269.png)

43. **Fixtures에 쓸려면 `어디서든 불러도 같은 객체가 생성되도록 캐싱==싱글톤이 보장`되어야한다.**

    - isSameAs로 확인해야한다.
    - **만약, 캐싱객체가 아니더라도, `eq/hC를 재정의하여 값이 같으면 같은 객체가 되는 값객체`가 되어야한다. 이 땐, isEqualTo로 정의한다.**

    ![image-20220805124337165](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805124337165.png)

44. **싱글톤 캐싱객체 or VO라서, `생성해서 쓰면 무조건 같은 객체`가 보장되었다면, `테스트에서 쓰인 객체들의 상수추출 -> Fixture로 옮겨서 재활용`할 수 있다. **

    - **상수가 되면 편하게 생성안하고 편하게 쓰면 된다.**
    - **`한글로 Fixture를 생성해서 쓰자`**
    - 상수 추출후, **Fixture로 옮기기전에 미리 다 상수로 바꿔놓으면 더 쉽다(`ctrl+H`)**

    ![7ed764cd-8939-486c-899a-0c81689cf785](https://raw.githubusercontent.com/is3js/screenshots/main/7ed764cd-8939-486c-899a-0c81689cf785.gif)

    ![image-20220805125353849](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805125353849.png)

45. **이제 `빈 생성자로 빈 상태값으로 시작하는 Ready의 [시작상태객체]`가 완성되었다.**

    ![image-20220805130924167](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805130924167.png)

46. **`시작객체`가 있다면, `같은 패키지로 몰아서 시작객체public을 제외한 나머지들은 default가시성`을 가지도록 해서, 외부에서 생성안되도록 한다면, `사전검증을 안해도 된다.`**

    - **만약, 생성자가 기본생성자라서 정의를 안해줬다면, `재정의 후 가시성변경`해줘야한다.**
      - **대표적인 예가 `유틸클래스(상태값없이 input->output static메서드만 가지는)는 생성자를 private가시성으로 변경`해주는 것이다.**
      - **생성자가 없는 클래스 -> `public으로 열여있으며, 재료를 바탕으로 상태값을 만들지 않는 클래스`**
    - client가 잘못입력할 수도 있기 때문에 **`중간에 생성되는 상태객체들`은 `외부에서 생성못하도록 가시성으로 막아주기`**

    ![9c833c96-8fe1-4e44-965e-681bbf771ffa](https://raw.githubusercontent.com/is3js/screenshots/main/9c833c96-8fe1-4e44-965e-681bbf771ffa.gif)


    ![image-20220805133739069](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805133739069.png)

47. **상태객체는 `trigger메서드(draw)`외에 `중도 stop`메서드와 `stop상태`를 가질 수 있다.**

    - ready **+ trigger**
      - blackjack
      - hit **+ trigger**
        - bust
        - ~~blackjack~~ : 블랙잭은 2장일때만 가능이다.
        - hit
      - hit **+ stop** : **중도stop 메서드**
        - **`stay` : 중도 stop상태객체 반환**

    - **stop메서드와 stop상태객체 2개를 다 만들어준다.**

      ![image-20220805175144554](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805175144554.png)

48. hit상태에서 stay를 호출하도록 테스트를 짠다.
    ![image-20220805175856032](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805175856032.png)

49. 메서드가 반환해야할 상태객체부터 만들고, **State 추상체상태의 hit에서 stay()를 만들면, 오퍼레이터로 등록된다.**

    ![image-20220805180022327](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805180022327.png)

50. **각 구현체는 외부에서는 추상체변수에서 다형성으로 사용되니, `특정 구상체(hit)에서 빨간줄 메서드로 만들면, 추상체인 인터페이스의 오퍼레이터`로 올라간다.**

    - 일단 인터페이스에 생성하고 -> **해당 구현체만 일단 구현해보자.**

      ![363f9945-4c87-4673-a7e5-4f418b00b0bb](https://raw.githubusercontent.com/is3js/screenshots/main/363f9945-4c87-4673-a7e5-4f418b00b0bb.gif)

      ![image-20220805180538834](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805180538834.png)

      ![image-20220805180525972](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805180525972.png)

51. **`특정 구상체에만 사용되는 메서드`가 등장했다.**

    - 상속관계면, 조합관계로 바꾼다.
    - **이미 인터페이스를 구현하는 조합관계의 구상체였다면**
      1. **일단 인터페이스에 올리고, 특정구상체만 자기메서드를 로직을 구현한다.**
      2. **마저 테스트를 진행해야하니 `나머지 구상체들은 로직 없이 구현만 해준다`**
      3. **이후 `해당메서드 테스트가 끝`나면, `특정구상체 외 사용하지 않는 구상체들에게 내리기 전`에 `중간에서 막아주는 추상클래스로 만들어지는 중간추상층`을 만들어준다.**

    - **일단 모든 구상체들도 구현은 하고 특정 메서드의 테스트를 진행한다.**

      ![cc2f7b9b-ca37-4345-9a92-387415551210](https://raw.githubusercontent.com/is3js/screenshots/main/cc2f7b9b-ca37-4345-9a92-387415551210.gif)

      ![image-20220805181221407](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805181221407.png)

52. **사용하지 않는 메서드를 `억지구현한 코드가 중복`이라서 `해당하지 않는 구상체들만 묶은 중간추상층(추상클래스)`를 도입하고, 거기서 구현해준다.**

    - 중간추상층은 **구상체로 내려가는 메서드를, 중간에 구현하여 중복되는 코드를 가운데서 막아준다.**

      - **이 때, 중간추상층은 카테고리로서, `Ready는 같이 묶기엔 의미가 다르다`**

      ![image-20220805181543133](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805181543133.png)

    1. **일단 인터페이스를 구현하는 `중간추상층` 추상클래스를 `형용사 상태`네이밍해서 만들고**
       - **stay할 수 없는 것들은 `이미 끝난 Finished`라고 하고**
       - **Ready는 논외로 성격이 달라, 놓아두고**
       - **Hit도 구현하므로 놓아둔다**
    2. **해당하는 구상체들은 추상클래스를 상속한다**
    3. **사용하지 않는 메서드는 `thr IllegalState`로 처리한다.**



53. **State를 구현한 추상클래스Finished를** 만들고

    1. abstract를 달고, 2개의 오퍼레이터 모두 구현해주되
    2. **먹어줄 것(stay -> 사용안됨)만 남기고, 개별구현할 것들은`@Overide 구현 메서드들을 자식들에게 다 내려보낸다`**

    ![ada195af-063e-4373-a796-eaceee8ea527](https://raw.githubusercontent.com/is3js/screenshots/main/ada195af-063e-4373-a796-eaceee8ea527.gif)


    ![image-20220805183950787](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805183950787.png)

    3. **하위 구상체들은 인터페이스 impl이 아닌 중간추상층인 `Finished`를 extends한다**

       ![image-20220805184042552](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805184042552.png)

54. **추상클래스 도입시 `중간에 먹어줘서 공짜로 물려주는 것들은 final을 달아서 자시이 수정 못하게 막아야한다`**

    ![image-20220805184221982](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805184221982.png)

    - **구상체가 도입되고, 추상체도 추가되었으니 다이어그램을 본다.**

      ![image-20220805184548517](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805184548517.png)

    - **추상클래스 도입후 `눈꽃모양의 final`을 확인하자.**

      ![image-20220805185002337](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220805185002337.png)



55. **모든 구상체에 대해 `stay()`메서드가 추가되었으니 메서드 테스트를 추가한다.**

    - blackjack, bust, stay -> finished -> stay호출시 예외
    - ready -> stay호출 시 예외

    ![image-20220806125452720](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806125452720.png)

    - **Stay 도메인객체가 추가되었으니, 생성 및 메서드 테스트를 해야한다.**

56. **상태패턴의 상태객체반환 with trigger되는 `결국엔 다른 객체의 상태값으로 포장`될 `객체`들은 `view에 넘기기 위해 getter를 무조건 가진다`**

    - **`trigger 등 set계열 메서드들 개발이 완료되면 getter도 개발`해야한다.**

    - **원래 `추상클래스의 중복필드처리`는 `부모필드는 생성자 정의없이 -> setter만든 뒤, setter주입`되어서 `자식이 super를 못쓰게 해야하는데..`**

57. **복잡한 구조에서 `getter`개발하기**

    - **현재는 복잡한 구조로 되어있기 때문에, `메서드 개발을 1개의 구현체에서부터 올리기`를 하진 못한다. `최상위 인터페이스State에서 정의후 하나씩 내려오기`해야한다.**

    ![image-20220806133501868](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806133501868.png)

    1. **최상위 인터페이스에 getter를 정의하되 `값의 반환`이 아닐 때는 `.get필드()`대신 `.객체()`형식으로 정의해본다.**

       - 인터페이스에서는 접근제한자가 public이 default이므로 두고 **반환형을 상태값 Cards로 주면서, 메서드명도 cards()의 필드명으로 만들자**

       ![09e19aec-4613-43ef-813f-ecf2fee9b6c8](https://raw.githubusercontent.com/is3js/screenshots/main/09e19aec-4613-43ef-813f-ecf2fee9b6c8.gif)


       ![image-20220806135501987](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806135501987.png)

    2. **개별 구상체(Ready, Hit)는 바로 구현하되, `중간추상층 Finished`에서는 해당오퍼레이터가 중복인지 확인한다.**

       - **getter오퍼레이터의 구현은 `getter구현 전에 필드값이 존재`해야하는데**

         - **중간추상층의 부모가 없다면, 바로 `자신의 필드를 반환`해주면 된다.**

         - Ready / Hit 구상체

         ![48e74868-8edf-456e-99b8-e8ba38a8c9b4](https://raw.githubusercontent.com/is3js/screenshots/main/48e74868-8edf-456e-99b8-e8ba38a8c9b4.gif)

         ![image-20220806135930790](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806135930790.png)

       - **`중간추상층에서 먹어준다면, [좋은 부모로서 생성자없이 setter로 -> private필드올릴 준비]를 해야한다` **

         ![image-20220806140117699](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806140117699.png)

    3. **3개 구상체에 대해 `공통getter`라고 판단이 들었으면, `final 템플릿`메서드로서 `중간에서 먹어준다`**

       ![c9ed91ed-d0dd-418f-8466-47dac1b52d4d](https://raw.githubusercontent.com/is3js/screenshots/main/c9ed91ed-d0dd-418f-8466-47dac1b52d4d.gif)

       ![image-20220806140435264](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806140435264.png)

       - **추상클래스는 무조건 public final** or protected abstract다

         ![image-20220806140531052](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806140531052.png)

    4. **문제는, `getter로 반환될 필드를 부모인 중간추상층에 올리는 순간`, 부모의 생성자가 정의되어 `자식이 super를 쓰는 불상사`가 생길 수 있다.**

       ![image-20220806140408780](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806140408780.png)

    5. **부모는 해당필드에 대해 `생성자가 아닌 public final setter`로 객체를 초기화시키며**

       1. **해당 부모의 `구상체 자식들은 안보이지만 setter를 이용해서 해당 필드를 채워야한다`**

          1. **맞는지 모르겠지만, setter로 인해 상태값 변화했으면, 변화된 객체를 반환해줘서 `체이닝`가능하게 한다**

             - 예시

               ![image-20220806141343001](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806141343001.png)

               ![image-20220806141236714](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806141236714.png)

          ![ad86e7b6-b7a6-4c9e-b66b-9a45db6744ba](https://raw.githubusercontent.com/is3js/screenshots/main/ad86e7b6-b7a6-4c9e-b66b-9a45db6744ba.gif)

          ![image-20220806141431436](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806141431436.png)

       2. **기존에 cards를 생성자 주입했던 구상체들은 setter로 변경해야한다.?!**

       

    6. **부모가 `getter를 먹으려면 + 공통의 필드 cards`까지 먹어야했다.**
       ![image-20220806141621141](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806141621141.png)

    7. **자식들은 getter구현 안한 상태 유지 + `공통필드 + 그 생성자까지 제거`해줘야한다.**

       ![dbbd4ebb-90b5-4ed0-a344-3de8cfc09e02](https://raw.githubusercontent.com/is3js/screenshots/main/dbbd4ebb-90b5-4ed0-a344-3de8cfc09e02.gif)

       ![image-20220806141930165](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806141930165.png)

    8. **자식들이 그동안 테스트에서 `new Blackjack( cards )`를 사용한 부분은**

       1. **부모에 위치하는 `부모 속 공통필드에 템플릿setter로 공급`하고 -> `템플릿getter`를 사용해서 해당 필드를 조회할 수 있다.**

          ![b01ac58e-55d8-4730-aa34-d4007ea55429](https://raw.githubusercontent.com/is3js/screenshots/main/b01ac58e-55d8-4730-aa34-d4007ea55429.gif)

       2. **생성자주입으로 사용하던 객체를 -> [부모공통필드로 옮기는 바람에 setter로]사용해야된다면, 코드변화가 많지는 않다**

          1. new 객체(  주입  )   -> new 객체**( ). setter**(  주입  )

             ![image-20220806142627252](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806142627252.png)

             ![image-20220806142650775](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806142650775.png)

             

    9. **getter구현이 완료되었으면 getter에 대해서 생각해보기**

       - **만약 view로 반환하는 객체가 `불변객체(불변일급 -> 상태변화시 새객체 반환)`라면, `DTO가 필요없고, public getter가 가능`하다.**
         - 객체의 **상태를 변화시키는 set계열을 호출해봤자 `내부에서는 새객체를 반환`되는 메서드만 제공**된다.
           ![image-20220806145015195](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806145015195.png)
         - **객체의 상태를 꺼내보는 get계열을 호출해봤자 **
           - **`컬렉션 필드라면, 이미 내부에서 생성자복사(얕은복사)`를 통해 `기존상태값과의 연결을 끊어놓은 상태`로 내준다.** 
           - **그 내부의 개별요소 객체들을 set계열로 호출해봤자 새객체가 반환된다.**
           - 개별객체들은 get계열로 호출해도 연결안되고 반환된다.
       - **view로 보내는 `컬렉션 필드 반환 getter들은 깊은 복사`를 이미 하고 있어야한다.**
         - **조작불가 + 깊은복사까지 이루어져야한다.**
         - **개별요소들을 view에선 건들이면 안되기 때문**
       - **암기getter**
         1. **`getter정의시 return 불변객체`라면, `public열어두기 가능 + dto없어도 됨`** 
            - **불변의 일급컬렉션 객체 반환 -> dto없는 public getter로 제공**
            - **불변객체 아니라면, `Dto로 만들어서 반환`**
         2. **`getter정의시 return 컬렉션필드라면, view로 보낼 땐, 깊은 복사로 못건들게 해서 반환`**
            - **불변의 일급컬렉션 `자신 내부의 컬렉션 필드 반환`**
              - server사이드 반환이라면, 얕은복사 반환후, 내부에서 객체들 조작
              - **view반환이라면,  `깊은 복사 반환 or DTO로 반환` 후, 내부 객체들 조작안되게**

    10. **Ready도 getter가 있어야하나?**

        - 0개 카드 확인? 1개 카드 확인? -> **갯수만 메세지보내서 확인하면 되는데, 내부까지??**

        - 선택이다
          1. **Ready는 `카드 2장채워질때까지==다른상태객체 될때까지` 값을 반환안하려면 `thr`로 막아주기**
          2. **보여줘도 되면 getter구현**

58. **`getter 추가 -> 중복필드로서 같이 움직이는 해당 상태값`이 추가된 상황에서**

    1. **`최상위 인터페이스에서부터 추가`되었으며**

    2. **구현시, `어느 구상체들도 thr로 예외없이 다 100% 다 구현`된 상태라면**

    3. **`구상체 100%구현 메서드 + 필드`로서 `중복제거를 위한 중간추상층`이 추가된다.**

       ![image-20220806171853571](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806171853571.png)

59. **추상클래스 이름은 Finished를 포함하여 모든 구상체를 아루는 형용사인 `Started(시작된 상태)`로 한다.**

    1. State인터페이스를 구현한 추상클래스를 만든다.

    2. **getter만 구현하여 막아주고, `구상체 100% 동일구현이 아닌` 나머지는 다 흘려보낸다**

    3. **중복된 필드의 구현이라면 `생성자 대신 public final 템플릿 setter로 정의`한다.**

       ![463ad1ff-3be7-4fee-a21a-29df67773e8e](https://raw.githubusercontent.com/is3js/screenshots/main/463ad1ff-3be7-4fee-a21a-29df67773e8e.gif)

       ![image-20220806172328738](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806172328738.png)

    4. **하위에 있는 추상클래스Finsished,  Ready, Hit는 State가 아닌 `Started중간추상층을 extends`하고 `중복되는 getter + 필드 + 생성자`를 모두 제거한 뒤, `필드를 사용하던 곳은 getter로 / 생성자로 받던 재료는 setter로` 변경해야한다.**

       ![2b588ebe-2d36-4432-91cb-cfe5e9921ec8](https://raw.githubusercontent.com/is3js/screenshots/main/2b588ebe-2d36-4432-91cb-cfe5e9921ec8.gif)

       - Finished

         ![image-20220806172934736](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806172934736.png)

       - Ready

         ![image-20220806173016138](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806173016138.png)

       - Hit

         - **자신의 필드 cards를 사용하던 곳은 `부모가 물려준 중복메서드인 템플릿 getter ->  cards()`를 내수용으로 사용한다.**
         - **자신의 생성자 주입으로 상태를 받았던 것을 `부모가 물려준 중복메서드의 템플릿 setter를 이용해 물려받는 -> 부모의 field를 채우고` -> 나는 getter cards()만 사용할 수 있게 한다**

         ![image-20220806173057219](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806173057219.png)

60. **추상클래스로 중복필드 + `생성자까지 제거 하다보니 -> 생성자가 없으면 public생성자`상태가 되어버렸다.**

    1. **하지만, `패키지내 중간객체 클래스` or `유틸메서드 클래스`의 생성자들은 `default와 private`으로 잠겨있어야한다.**
       ![image-20220806174625925](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806174625925.png)

    2. **패키지내 시작객체 `Ready를 제외한 모든 구상 상태겍체들의 보이지 않는 기본생성자를 재정의해서 default로 변경`해주자**

       ![01daf272-6ec1-4677-aaa8-56914dc508bf](https://raw.githubusercontent.com/is3js/screenshots/main/01daf272-6ec1-4677-aaa8-56914dc508bf.gif)

       ![image-20220806174957587](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806174957587.png)

61. **상태객체의 인터페이스 `오퍼레이터는 최소 4개+ @가 필요`하다**

    1. trigger 메서드: draw()

    2. 중단 메서드:  stay()

    3. 현재상태값 반환메서드: getter cards()

    4. **`is특정[중간추상층]인지` 확인 메서드: boolean isXXXX()**

       - **여기서는 stay개발시 불가하여 묶여진 `Finished 형용사 중간추상층`이 이미 있으니 `끝난 상태냐`라고 물어볼 수 있다.**

         - **`특정 구상체인지는 물어보지 않는다. 그놈빼고 다 false를 대답하기 때문에` -> `특정 구상체인지는 물어보지말고 메세지를 던진다.`**

       - **`다형성 인터페이스에 존재하는 boolean 메서드는 -> 구상체들이 구현만 해주면 알아서 개별 답변되는 메서드`다**

         ![image-20220806180149358](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806180149358.png)

62. State인터페이스에 **특정상태(그룹, 중간추상층) 확인 메서드를 구현하고**

    - Started는 건너띄고, **Finished에서는 True / 나머지 구상체 Ready, Hit에서는 False를 return하자**

      ![image-20220806182142251](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806182142251.png)

      - **Finished에서 구현했다면, 추클로서 중간에 중복을 먹어 구현하였으니 final을 달아야한다**

      ![cb59d4e9-72ca-45c4-ac00-88d08c67c334](https://raw.githubusercontent.com/is3js/screenshots/main/cb59d4e9-72ca-45c4-ac00-88d08c67c334.gif)

      ![image-20220806182052780](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806182052780.png)

      ![image-20220806182039996](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806182039996.png)


      ![image-20220806182119187](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806182119187.png)

63. **상태객체의 인터페이스 `오퍼레이터는 최소 4개+ @가 필요`하다**

    1. trigger 메서드: draw()
    2. 중단 메서드:  stay()
    3. 현재상태값 반환메서드: getter cards()
    4. is끝난상태[중간추상층]인지` 확인 메서드: boolean isXXXX()
    5. **현재 상태객체를 만드는 상태값Cards를 이용해서, `정보를 가진놈이 기능을 가진다`**
       - **정보전문가패턴에 따라, 상태값으로 가능한 연산은, 가진 놈이 한다.**
       - cards를 가진 **state객체가, cards로 계산하는 것을 한다.**

64. **`상태별 수익계산 profit()`메서드를 State인터페이스에 구현해보자.**

    - 만약, **`추상체변수만의 구상체별 자동 구현`**을 모른다면? **state객체를 보유한 player 내부에서**

      - **`state.isBlackjack()`?  -> 블랙잭 계산**

      - **`state.isBust()`?  -> 0 반환**

      - **`state.isStay()`?  -> Stay 계산**

      - 하나하나 다 확인해서 그에 따른 계산을 해줘야한다.

        ![image-20220806183305823](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806183305823.png)

    - **추상체변수의 `구상체별 기능 구현`은 `전략패턴처럼 구현만 하면 알아서 적용`된다.**

      - **이미 외부생성(전략패턴) or trigger메서드(상태패턴)에 의해**  State state안에는 Blackjack이나 Bust나 Stay가 담겨있는 상태다
        - **개별로 구현만 해주면, 알아서 작동한다.**
      - **대신, 알아서 계산안되어야할 놈들은 thr나 예외처리 해놔야한다.**

    - **추상체 변수의 `상태이용 개별구현 메서드`는 구상체를 물어보지말고 구현만 해놓으면 알아서 적용된다.**

      - **상태값이 일반 객체였으면... 물어보고 판단했을 것인데... 추상체변수인 순간 알아서 구현됨을 생각하자.**

65. **State 인터페이스에 `전략패턴과 달리, 구상체들이 가진 상태값cards`를 바탕으로 계산하는 기능인 `profit`을 구현하되, `파라미터로 [계산마다 바뀔 수 있는 필요정보 betMoney]`를 인자로 받자**

    - **구상체들이 상태값을 가지는 경우, 인터페이스 오퍼레이트는 상태값을 이용한 계산 기능도 추가할 수 있다.**
      - getter도 그랬고.. 

    - **인터페이스에 메서드를 올리는 순간 `알아서 구상체별로 개별구현되는 메서드구나` 생각하자**

      ![image-20220806185128475](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806185128475.png)

    - **계산의 대상은 `이미 끝난 상태의 Finished`만 해당이 된다.**

      - Ready와 Hit는 **예외상황으로 먼저 막아놓자.**

        ![7512c061-e33e-47a6-be67-89107122610d](https://raw.githubusercontent.com/is3js/screenshots/main/7512c061-e33e-47a6-be67-89107122610d.gif)

        ![image-20220806185930878](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806185930878.png)

66. **`구상체들에서 개별구현되니 일단은 중간추상층(Finished)는 건너띄고 구현`한다**

    - Blackjack -> betMoney의 2배 반환
      ![image-20220806190556279](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806190556279.png)
    - Bust -> **-1을 곱해 반환 (그만큼 손해)**
    - Stay -> 추가정보가 더 필요하니 계산말고 그냥 반환

67. **중간추상층이 좋은 부모가 되려면, `공통점이 아예 없는 개별구현로직 조차, 개별구현훅메서드를 래핑하고 있는 public final 템플릿메소드`로 가지고 있고, `서로 다른 로직은 protected abstract 훅메서드만 내보내줘야한다.`**

    1. CompareFile(ctrl+D)로 **구상체 메서드들의 공통점과 차이점**을 살펴보고, **`어느부분까지를 훅메서드로 뺄 것인지 생각`한다.**

       - **상수만 서로 달라도, 해당부분을 훅 메서드로 뺄 수 있다.**

    2. **구상체들 중 `서로 다른 훅 부분에  가장 파라미터가 많이 사용되는 로직`을 가진 구상체 1개를 선택하고 `해당 메서드를 중간추상층으로 잘라내서 올린다`**

    3. **올린 public 템플릿메서드 내부에서 `다른 부분만 내수용 메서드 추출(구상체private -> 추상체protected abstract)의 훅메서드로 만든다`**

       - **올리고 나서 훅을 만든다면, `메서드 추출후 가시성을 private -> protected abstract로 직접 변경`해줘야한다.**
       - **올린 템플릿메서드는 final로...**

    4. **나머지 구상체들은, `훅메서드를 구현`하면서, 기존 구현메서드의 다른 부분만 훅에 입력한 뒤, 기존 메서드는 삭제해준다.**

       ![e46c06eb-6722-429a-8311-3ff3f0fb6af9](https://raw.githubusercontent.com/is3js/screenshots/main/e46c06eb-6722-429a-8311-3ff3f0fb6af9.gif)

       ![image-20220806225502853](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806225502853.png)

       ![image-20220806225516439](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806225516439.png)

    5. **public 템플릿메소드는 final을 달아준다. 깜빡했으면...**
       ![image-20220806225611306](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806225611306.png)

68. **`중복제거 등 추상화/상속 관련작업`이 끝날 때마다, diagram을 보자**

    - **복잡하다면, 생성자+method만 키고 보자.**

    ![image-20220806230604477](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806230604477.png)

    - **추상화 레벨을 보고 싶다면, `바로 위의 추상클래스에 데고 usage(shift+F12)`를 통해 `어떤 놈들이 나를 직접 extends했는지`확인하면 된다.**

      ![image-20220806231001237](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806231001237.png)

69. **Finished `기존 중간추상층과 추상화레벨을 맞추기` 위해 `Ready`와 `Hit`구상체의 공통점과 차이점을 확인한다.**

    - draw, stay는 구현내용이 개별구현으로 100% 달라서 일단 유지하고

    - **profit()과 isFinished()는 비슷한게 아니라 완전히 동일**하므로, **추상층에 올릴 것이다.**

      ![image-20220806232414858](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806232414858.png)

70. **finished에 대항하는 isFinished false를 가진 `형용사 중간추상층 Running`을 만든다.**

    ![](https://raw.githubusercontent.com/is2js/screenshots/main/image-20220323000036426.png)

    1. Started를 구현하고, Ready에서 공통메서드만 중간에서 먹어준다.

    2. abstract클래스며, 중복코드를 제거하는 템플릿메소드들은 final을 달아준다.

       ![449c9322-56b1-419d-88c8-13a04d0463b5](https://raw.githubusercontent.com/is3js/screenshots/main/449c9322-56b1-419d-88c8-13a04d0463b5.gif)

       ![image-20220806232957330](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806232957330.png)

       ![image-20220806233009526](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806233009526.png)

       ![image-20220806233220821](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220806233220821.png)

71. 이제 추상화레벨이 맞춰졌다.

    ![image-20220807000217047](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807000217047.png)



72. **부가적인 처리로서, `자식들은 훅메서드만 가지도록 좋은 부모 만들기`의 일환으로**

    - **100% 로직이 달라도 메서드명만 같다면, 100% 훅메서드인 포장 public final template으로 올리자.**

    ![image-20220807001054996](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807001054996.png)

    - **문제는 오퍼레이터 메서드명은 유지되어야하는데, 100% 훅메서드명을 동일하게 못잡으니 어렵다.**

      - draw -> drawFrom

      - stay -> stayEach

        ![ef036983-3849-4d1c-a20a-f62a28f58644](https://raw.githubusercontent.com/is3js/screenshots/main/ef036983-3849-4d1c-a20a-f62a28f58644.gif)

      ![image-20220807001808286](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807001808286.png)

      ![image-20220807001832475](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807001832475.png)

      ![image-20220807001851504](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807001851504.png)

    - 자식 구상체들은 훅만 소유

      ![image-20220807002018941](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807002018941.png)

73. **Finished에서도 좋은 부모를 만들기 위해 템플릿메서드로 올리고, 자식들은 훅만 소유하도록 변경해보자.**

    ![image-20220807002057853](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807002057853.png)

    - draw를 중간추상층의 템플릿메서드로 올리자

      - **100%로직이 같아서 훅메서드를 뽑을 필요가 없었다.**

        ![1388bb0d-489b-470a-98f9-ab96ad095260](https://raw.githubusercontent.com/is3js/screenshots/main/1388bb0d-489b-470a-98f9-ab96ad095260.gif)

      - **final을 달아주는 것을 깜빡해서 달아주자.**

        ![image-20220807002635494](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807002635494.png)

      ![image-20220807002715139](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807002715139.png)

    

74. **그러고보니, `중간추상층 2개도 상위 추상클래스로 올릴 수 있을 것`같은데, `훅을 메서드 vs 안가진 메서드` 등의 차이도 있고, 카테고리 문제도 있어서 일단 여기까지만 처리한다.**

    - **구상체(자식들)은 모두 훅메서드만 가졌다.**
    - 전략패턴으로 변경된다면, 전부 전략메서드만 구현하게 되는 것이다.

    ![image-20220807003348526](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807003348526.png)

    

75. **이제 `상태객체를 추상체변수로 소유해서 사용하는 Player`를 다른 패키지에서 생성한다.**

    - **상태객체는 trigger에 의해 계속 변화므로 `not final 필드초기화`+ `set계열메서드`로 가지고 있는다.**

    - **상태를 바꾸는 set계열이지만, `이미 정해진 종류의 상태를 가진다면 set대신 public void toggle`에 가깝다.**

      - 필드초기화시 유일하게 열린 Ready 로 만든다.

      ![d8da10a5-85d0-4523-8dd7-7b5c05fc2bd9](https://raw.githubusercontent.com/is3js/screenshots/main/d8da10a5-85d0-4523-8dd7-7b5c05fc2bd9.gif)

      ![image-20220807010832575](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807010832575.png)

76. **`메서드가 호출객체(자신)의 상태가 변하는 메서드(setter/toggle계열)`때, `호출객체(state) 상테에 따른, 제한[호출시 예외발생코드가 존재]이 있는 action`이라면, `if문으로 [예외발생 코드 상태로 진입못하게 하는] 상태condition을 확인`후 시행해야한다.**

    - **state가 blackjack, bust, stay로 업데이트되었다면,  draw를 호출 시 예외가 발생한다.**
    - **애초에 해당 상태(Finished)가 아닐때만 호출하도록 condition을 걸고 시행한다**
      - **`예외발생 코드를 짜놨찌만, 그쪽에 도달하지 못하게 막아주는 것이다.`**

    ![image-20220807011225148](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807011225148.png)

77. **만약, `매번 확인하며 자신을 업데이트`하는 작업을 `여러번 && 조건불만족시까지 반복`하고 싶다면 -> `if condition + set계열 action의 반복문이 while`이다.**

    - **이 때, 파라미터는 `Cards의 컬렉션 뭉태기`가 아니라 `반복이 끝날때까지, iterator로서 1개씩 재료 제공메서드()getter를 제공해주는 객체`가 파라미터로 와야한다.**

      - **1번일 때 파라미터가 Card라고 해서 -> Cards 나 Cards컬렉션을 생각하지말고 `iterator같은 객체`를 생각하자. `public 메서드의 파라미터 == 외부에서 생성되어 들어옴 -> 도메인 객체를 외부에 함부러` -> 허용하지말자**

        - **`my) 데이터(도메인)객체를 함부로 외부에서 생성하도록 public 메서드의 파라미터로 받지말자.` -> `내부에서 생성하거나, 내부에서 iter로 받자` -> `도메인객체의 메서드가 public으로서 외부에 공개된 것이면 상관없지만, 일단  내부생성/캡슐화 조달(iter)로 받자`**

      - **반복이 끝날때까지, 매번 재료를 1개씩 제공해줘야한다.**

        ![92e5fda9-5ab5-4e79-84dd-43f21d423298](https://raw.githubusercontent.com/is3js/screenshots/main/92e5fda9-5ab5-4e79-84dd-43f21d423298.gif)

      ![image-20220807012819734](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807012819734.png)

78. 카드를 1장씩 제공해줄 수 있는 Deck을 만들어보자.

    1. **Deque의 재료 컬렉션을 생성할 땐, `ArrayList`로 일단 생성하고**

    2. **제공할 때, 얕은복사(생성자복사)시 `new ArrayDeque<>( list )`의 `생성자 복사시 형 변환하여 deque를 제공`해주면 된다.**

    3. **이 때, `랜덤으로 섞인 것을 제공해주려면, ArrayList를 먼저 shuffle한 뒤, deque로 생성자복사해서 제공`한다**

       ![image-20220807123437674](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807123437674.png)

    4. **가변 콜렉션 -> append을 써서 생성하는 것은 `stream으로 묶어서 일괄처리`가 가능하다.**

       1. 2중 for문은 **stream -> map -> stream -> 기존map을 flatmap후 바깥에서 collect**하면 된다.

          ![51f7b591-8476-421e-ae82-602aff5abaaa](https://raw.githubusercontent.com/is3js/screenshots/main/51f7b591-8476-421e-ae82-602aff5abaaa.gif)


          ![image-20220807124141031](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807124141031.png)

          ![image-20220807124150334](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807124150334.png)

          ![image-20220807124206100](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807124206100.png)

    5. **생성자에서 쓰이는 `내부context(보라색)은 필드`인데, `내부 필드 초기화`는 `메서드 추출시 안뽑히고 내수용메서드가 되어버리니, 필드 초기화하는 = 우항값을 지역변수로 뽑아서, [필드 = 초기화]가 아닌 [지역변수 할당 = ]해서 필드초기화는 제외시키고, 위쪽만 메서드 추출하자.`**

       - **`메서드 추출시`, `필드 초기화 부분은 제외`시키자!**

       - **`메서드 추출시, 다른 곳에 위임되지 않을, 내부context필드 초기화 && 재할당은 할당값(우항)만 따로 지역변수로 빼놓고, 해당 context를 제외시키고 추출한다`**

         - **만약, 내부context를 다른쪽에 위임하는 로직이면, 내부context를 지역변수로 빼서 위쪽으로 놓고, 해당 지역변수가 파라미터로  잡히게 한다.**

         ![e90db055-47b1-4b09-87e0-6cfefaeea16f](https://raw.githubusercontent.com/is3js/screenshots/main/e90db055-47b1-4b09-87e0-6cfefaeea16f.gif)


         ![image-20220807125203951](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807125203951.png)

         ![image-20220807125500504](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807125500504.png)

79. **랜덤이 포함된 로직은,  보이자마자, `전략패턴적용`을 위해, `메서드추출 -> 전략객체.메서드()호출 -> 전략객체에 위임 -> 전략 인터페이스 생성`으로 빼놓자.**

    1. **`랜덤이 껴있는 전체로직( 객체 반환까지 )`을 1개의 메서드로 추출한다.**

       ![b92ff93b-fe78-4ced-98ce-19005afb7f4b](https://raw.githubusercontent.com/is3js/screenshots/main/b92ff93b-fe78-4ced-98ce-19005afb7f4b.gif)
       ![image-20220807133924318](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807133924318.png)

    2. **`위임을 위해 예비전략메서드의 인자에 new 랜덤전략객체()`를 넣어서 `파라미터로 추가`한다. 내부에선 사용하진 않는다.(위임하기 위해 만든 객체라서 원래 사용된 상태)**

       - 파라미터에 전략객체가 있어야 -> 파라미터 속 1개의 객체로 위임할 수 있다.

         ![ba7258b3-b722-4898-844e-885391718b77](https://raw.githubusercontent.com/is3js/screenshots/main/ba7258b3-b722-4898-844e-885391718b77.gif)
         ![image-20220807134122277](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807134122277.png)

    3. **`내부 메서드가 있다면, 모두 new 전략랜덤객체를 파라미터로 추가`해준다.**

       - **내부 메서드들 내부의 `this context`도, 모두 전략객체로 교체**

         ![dc294698-a194-497d-b171-1f2202bfb67b](https://raw.githubusercontent.com/is3js/screenshots/main/dc294698-a194-497d-b171-1f2202bfb67b.gif)

         ![image-20220807134713089](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807134713089.png)

         - **this가 메서드레퍼런스에 껴있으면, 다시 람다식으로 풀어서 `파라미터 추가`**

           ![793c853e-9619-4b27-8bb4-72be98de434d](https://raw.githubusercontent.com/is3js/screenshots/main/793c853e-9619-4b27-8bb4-72be98de434d.gif)

           ![image-20220807134845380](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807134845380.png)

       - **보라색 context가 껴있으면, 파라미터로 추출**

    4. **내부메서드들부터 F6으로 위임한다**

       1. **만약, 바깥부터 하면, 내부 내수용메서드 == Deck메서드 == Deck가 파라미터로 달린체로 위임된다**

          ![image-20220807135027659](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807135027659.png)

       2. **`아주 안쪽 && 먼저 실행되는 내수용 메서드들부터 위임하자`**

          ![e0ddd88d-dfe2-4f6d-ae02-75f3774f3187](https://raw.githubusercontent.com/is3js/screenshots/main/e0ddd88d-dfe2-4f6d-ae02-75f3774f3187.gif)

          ![image-20220807135410544](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807135410544.png)

          ![image-20220807135604705](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807135604705.png)

          - **위임된 내수용메서드들은 수동으로private으로 바꿔주자**.(옮길때는 위임해준 쪽으로 넘겨줘야해서 public이 자동으로 걸린다.)

            ![image-20220807140102596](../../../../../../../AppData/Roaming/Typora/typora-user-images/image-20220807140102596.png)

            

    5. **이제, 특정 전략객체가 아닌, `전략인터페이스를 파라미터를 통해 외부에서 주입`받아서 소유하도록 하기 위해, `구상체로부터 전략인터페이스를 만들어 올린다`.**

       1. 구상클래스에서 @Override -> extract interface -> 전략인터페이스 생성 -> **전략메서드만 추출**

       2. 구상체 사용처(Deck)에서 **파라미터 추출 -> 생성자로는 전략인터페이스가 들어오도록 수정**

          ![04fcb343-b1f7-4482-923a-b29a430261d8](https://raw.githubusercontent.com/is3js/screenshots/main/04fcb343-b1f7-4482-923a-b29a430261d8.gif)

          ![image-20220807140331139](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807140331139.png)

80. **이제 Deck에서는 랜덤Deque카드들을 소유한 상태이므로 Player에게 pop으로 1개씩 제공해준다.**

    - Player는 while문으로 상태확인하면서, **Deck으로부터 1개씩 재료를 iterator처럼 제공받는다.** 

      ![2a024204-2c01-4410-8846-43e87e0130b9](https://raw.githubusercontent.com/is3js/screenshots/main/2a024204-2c01-4410-8846-43e87e0130b9.gif)

      ![image-20220807140606654](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807140606654.png)

81. **하나의 기능만 뺀 전략패턴은 람다식을 편하게 사용하도록 애너테이션을 붙여준다.**

    ![image-20220807140849701](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807140849701.png)

82. **RandomCardGenerator는 List로 종류별로 만든 카드 List -> Shuffle후 -> Deque로 반환**해주는데, **`Deque의 재료 List는 1번만 생성해놓고, 필요시 shuffle후 생성자복사만 매번 해주면 된다.`**

    - **즉, 카드종류별 List는 `static 필드에 미리 1번만 생성해놓으면 된다.`**

    ![95fd73d8-8e5c-488f-8f2a-990018331ded](https://raw.githubusercontent.com/is3js/screenshots/main/95fd73d8-8e5c-488f-8f2a-990018331ded.gif)

    ![image-20220807223426856](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807223426856.png)

83. **이제 Random로직이 보여서 묶어서 만든 전략패턴의 `수동전략 생성 or 람다식 대입`을 만들어보자.**

    - **수동전략은 사실상 Test에서 밖에 안쓰이므로 `도메인에 전략객체 생성을 안한다`**
    - **람다식으로 바로 대입이 불가능하면, `수동전략 클래스를 testutil에 추가`해준다.**

84. **전략객체가 주입되는 객체를 생성하고, `전략객체 주입부`에 `가상인자 람다식으로 [전략메서드 대신구현]`이 가능한지 보자.**

    - 확인해보니 , Deck의 생성자에서 쓰고 있다 -> **테스트에서 new Deck() 을 만들자.**

    ![image-20220807232206299](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807232206299.png)

    ![image-20220807232220504](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807232220504.png)

    ![image-20220807232136058](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807232136058.png)

    - **람다식으로 `전략메서드 로직을 구현하여 시그니쳐에 맞는 응답`을 해줄 수 있는지 확인하자.**

      - **전략메서드는 `Deque<Card>`인 `new ArrayDeque<Card>();`를 채워서 응답해주면 된다.**

      ![image-20220807232417754](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807232417754.png)

    - **람다식 내에서 수동으로 채워주기엔 무리가 있으니 `위쪽에서 지역변수로 채워놓고, 해당 지역변수를 넣어줘도 된다.`**

      ![image-20220807232534896](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807232534896.png)

    - **카드2, 5를 수동으로 채워서 deck을 pop해보면, 뒤쪽 5가 나온다**

      ![f832a82d-af31-4fe9-b9b4-6f6eec5de5e7](https://raw.githubusercontent.com/is3js/screenshots/main/f832a82d-af31-4fe9-b9b4-6f6eec5de5e7.gif)

      ![image-20220807234741623](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807234741623.png)

    

85. **`수동으로 전략메서드 시그니쳐 응답값 구현`을 반복해서 사용할 예정이라면 `메서드 추출후, Fixed수동전략객체Class에 위임`하여 재활용한다.**

    - **이 때, `수동으로 넣어주는 값들을 변수로 빼서 사용할 수 있다.`**

      - 랜덤카드생성은 정해진 수의 카드만 가능

    - **수동카드생성은, `파라미터로 원하는 요소들로 짧게 구성 가능`**

      ![0e9ce492-833c-4223-9967-e9ba993ed2d2](https://raw.githubusercontent.com/is3js/screenshots/main/0e9ce492-833c-4223-9967-e9ba993ed2d2.gif)

      ![image-20220807235828447](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807235828447.png)

    - **원하는 수만큼 받을 수 있게 `2번째 파라미터부터를 가변배열로 받아서 처리`한다**

      ![ff728444-5533-42f4-b85a-46dc1df35067](https://raw.githubusercontent.com/is3js/screenshots/main/ff728444-5533-42f4-b85a-46dc1df35067.gif)

      ![image-20220808000033000](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220808000033000.png)

86. **이 때, `테스트용 수동 전략이 적용된 객체`생성까지를 `하나의 fixture생성`으로 보고 묶을 수 있다.**

    ![5c74f3aa-ecd0-44be-8def-b2e385d5a2cd](https://raw.githubusercontent.com/is3js/screenshots/main/5c74f3aa-ecd0-44be-8def-b2e385d5a2cd.gif)

    ![image-20220808001610654](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220808001610654.png)

87. **캐싱객체 or 불변Vo객체의 Fixture 모음이 아니라 `1개의 FixtureGenerator`로서 `testutil패키지지 > FixtureGenerator클래스를 만들고, 생성 유틸메서드로 추출`하여 재활용할 수 있다.**

    1. **testutil패키지 아래 `FixtureGenerator`** 클래스를 만든다

    2. **private메서드를 유틸메서드(Fixture정팩매)로서 `public static`을 붙인 뒤 `위임`한다**

       ![4a1139ce-e8ef-4c9e-8517-65e790c15489](https://raw.githubusercontent.com/is3js/screenshots/main/4a1139ce-e8ef-4c9e-8517-65e790c15489.gif)

       ![image-20220808002002988](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220808002002988.png)

       ![image-20220808002015770](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220808002015770.png)

88. player는 카드1장 받으면 Ready이 되어야한다.

    ![f4b91d9f-4f26-40f7-a902-7abf144f62df](https://raw.githubusercontent.com/is3js/screenshots/main/f4b91d9f-4f26-40f7-a902-7abf144f62df.gif)

    ![image-20220807224704847](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224704847.png)

    - **하지만, 최초의 Ready상태에서 Started에 선언된 공통필드가 `null`상태라서 getter호출시 에러가 난다.**

      ![image-20220807224602497](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224602497.png)

      ![image-20220807224613893](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224613893.png)

      ![image-20220807224629139](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224629139.png)

      ![image-20220807224536611](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224536611.png)

      ![image-20220807224642213](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224642213.png)

89. **교훈) `not final + setter계열 조합 for좋은부모`는 null참조가 될 수 있으니 `반드시 초기값 필드초기화를 해주자`**

    ![image-20220807224811919](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224811919.png)

    ![image-20220807224832856](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807224832856.png)

85. 2장이상 draw하면, Ready가 아닌 상태가 된다.

    ![image-20220807225214117](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807225214117.png)

86. **Player가 Deck을 전달받으면 finished가 될때가지 draw로 뽑아간다.**

    - **이럴 경우, `hit상태도 !finished라서 계속 반복하여 stay를 못한다`**

    ![image-20220807225449880](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807225449880.png)

    - **deck을 받아도 finished까지 반복이 아닌 1장만 받도록 수정해보자.**

    ![image-20220807230956468](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807230956468.png)

    ![image-20220807231031376](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220807231031376.png)

    - **만약, ! state.isReady()가 존재했다면... 연속해서 Ready아닐때까지 받으면 좋으련만..**
      - **이 draw는 Ready를 벗어나기 위한 것으로 1번밖에 안쓰인다.**
        - **draw는 범용으로 finished전까지 호출되어야하는 것이 맞다**
        - **그러나, stay호출을 무시 ready -> hit -> finished로 가기엔 finished까지 가기엔..**
          - stay상태는 hit -> stay()메서드호출만 가능한 finished상태임.
      - **isReady는 isFinished(끝난후 호출가능한 메서드 ex> profit 등이 있는 중요한상태)와 달리, 오퍼레이터로 작성하기엔.. 무리가 있다.**
        - Ready빼고 finisehd그룹 -> 공통 false / Hit -> false로 2개만 주면 되긴 하는데..





## 분기문 자체를 enum에 [찾을 때 지연실행될 로직]으로서 매핑할 수 있으며, 이미 시그니쳐가 정해진 함수형인터페이스를 이용해 돌면서(values().stream.filter내부) 지연 수행(.test)될 분기문을 [가상인자+람다식으로 실시간 외부 구현]으로 매핑해야한다

### (2) [로또] 

1. 도메인 및 객체 산출이 어렵다면 controller부터 정제된 input을 받고, output도 받환해야하는 **service부터 출발**한다.

   - 로또부터? 로또번호부터? 로또 게임부터? 로또 서비스부터?
     - 정제된 입력을 받아 구현할 수 있다면 서비스부터
   - 블랙잭 카드부터?  블랙잭 게임부터?  블랙잭 서비스부터?
     - 정제된 입력은 이름 밖이고 카드를 사람당  랜덤 2장 뽑아야해서..  그걸 지서 
       - 2장 받는 카드부터

2. **`서비스부터 짠다면, input -> output까지 메인 흐름을 생각`해야한다.**

   - 입력1: 사용자의 로또번호 + 보너스번호 입력
   - 입력2: 당첨번호 입력
   - 출력: 당첨등수 응답

   ![image-20220728162510238](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728162510238.png)

3. **정제된 input -> string을 `split등 전처리를 다 끝낸 원시형` + `컬렉션(List.of() - 어차피 불변객체 넣어줄테니까, 조작불가능 얕은복사 사본)`을 사용해서 만들어준다.**

   - 아직 도메인 객체 추출을 못했으니, `원시형 + 컬렉션으로 작성`한다

     ![image-20220728163518798](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728163518798.png)

4. **테스트할 메서드는 `무조건 응답하도록 먼저 작성`하며 이 때, `응답값은 [넣어준 인자에 대한 case값을 응답]`을 해줘야한다**

   - 만약, 로또번호 vs 당첨번호 **인자 입력을 1등 번호로  예시case로 넣어줬다면**, 그 **인자 case에 맞는 1등이 응답값으로 반환해야한다(아무거나 반환X)** 
   - **인자가 3개이상이면, 엔터쳐서  줄바꿈해준다.**

   ![image-20220728164141495](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728164141495.png)

5. 테스트 성공시 refactor -> 다음case (2번째부턴 일반화함)를 준비한다.

   ![image-20220728164441770](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728164441770.png)

6. **2번째 case를 넣기 전에, `1case 인자를 그대로 사용한 체 2메서드를 복붙 생성`하고 이 상태로 로직을 짜면서 `if등을 활용해 1case 통과`하도록 짜도 된다.  `1case통과 시`하면 `2case에 맞는 인자로 변경`하여서 본격적으로 짜도 된다.**

   ![image-20220728170621819](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728170621819.png)

7. 새로운 case(2번째라서 일반화)를 추가하되, **`2번째 case추가` == only 1case -> 일반화로서 `일반화 로직 추가` -> `2메서드로 작성`**한다 

   1. **서비스는 상태값이 없으므로 지역변수에 결과값이 나올 것인데, `1case가 통과하도록 if를 걸어주며 짠다`.**
   2. 컬렉션 vs 컬렉션 비교는 **반복문 + 요소vs컬렉션 비교 + 업데이트 지역변수**가 사용된다.

   ![image-20220728170431531](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728170431531.png)

8. 1case가 통과되도록 짠 뒤 -> 2case(일반화 케이스)로 짠다

   - 이 때, ValueSource(고정 값 1개)나 
   - CSVsource(고정 값 2개이상)나 
   - MethodSource(객체 이상)을 활용해서 **경계값 케이스 위주로 확인한다.**

   ![image-20220728175850278](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728175850278.png)

   - 통과되면 2메서드로 기존테스트 돌린 뒤 대체

9. **매칭5개가 2등이 아니라, 매칭 5개 + 보너스볼이 2등이고, 5개는 3등이다.**

   - 만약, 6-5-4 순으로 내려온다면 (7-매칭갯수)만 return하고 분기는 없어질텐데
     - **예외적인 상황이 발생하므로 if분기를 가지고 가야한다.**

   - 중간에 예외를 처리하기 위해 if분기로 보너스볼 매칭까지 확인해야한다.

   ![image-20220728180710783](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728180710783.png)

   ![image-20220728181122812](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728181122812.png)



10. **service 메서드를 통해 `정제된input`에 도메인 지식이 쌓였으면, `SERVICE에서 input중 가장작은 단위의 input부터 예외발생해야하는 case`를 작성하고 -> `도메인에서 검증로직`을 작성한다. `이후 그다음 input단위인 컬렉션에서 예외발생 case`를 작성하고 -> `도메인에서 검증로직`을 작성한다.**

    1. **service에서 `가장작은단위 input에 대한 예외 case`를 작성한다.**

       ![image-20220728183332991](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728183332991.png)

    2. 원시값에 대한 검증을 위해 **`검증이 필요한 원시값`을 `new 때려서 도메인객체로 포장`한다.**

       ![1c4cb04e-9b9e-4cc7-b508-8104852a4357](https://raw.githubusercontent.com/is3js/screenshots/main/1c4cb04e-9b9e-4cc7-b508-8104852a4357.gif)

       ![image-20220728190348563](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728190348563.png)


       ![image-20220728190403725](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728190403725.png)

    3. 현재 1개인자만 포장해줬기 때문에 컴파일에러가 뜬 상황임. 먼저 수정해준다.

       ![image-20220728191113904](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728191113904.png)

       

11. **인자를 1개만 포장했다면 -> `전체 인자 다 포장`한 뒤, `메서드의 파라미터도 변경`해줘야한다.**
    ![image-20220728191545604](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728191545604.png)

    - **`이 때 쓰는 것이 오버로딩`이다.**

      - 메서드의 파라미터 타입이나 시그니쳐가 달라졌을 때
      - 기본값을 입력해야할 때 ex> 꼬리재귀 재귀함수 최초호출 기본값 인자

      ![d64320e3-eb1c-4789-b252-3953a0357cf5](https://raw.githubusercontent.com/is3js/screenshots/main/d64320e3-eb1c-4789-b252-3953a0357cf5.gif)

    - **기존의 원시값컬렉션인자 -> 객체 컬렉션인자로 가려면, `기존 메서드 내부에서 포장처리`를 한번 해줘야 한다.**

      1. 원시값컬렉션 파라미터 메서드 내부에서 ->  도메인 컬렉션으로 변경하여, 도메인컬렉션 파라미터 메서드를 호출하도록 한다.
         ![image-20220728192253411](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728192253411.png)

      2. **원시값 로직은 `아직 일급컬렉션을 도입안했다면, 로직을 그대로 도메인컬렉션 메서드 내부로 옮길 수 있다`**

         ![image-20220728192341780](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728192341780.png)

      3. **다만, 다만 `도메인 컬렉션(List)가 contains( 단일도메인 )처럼 원시값처럼 도메인을 사용하려면, eq/hC재정의`를 해줘야한다.**

         ![image-20220728192450626](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728192450626.png)

      4. **`다시 원시값으로 인자 입력이 가능`해진 상태이므로 `서비스 호출시 에러가 나도록, 원시값 인자 입력으로 바꿔준다`**

         - 만약, 도메인 인자를 입력하면 **service메서드 진입전 도메인 자체에서 에러가 난다.**

         ![image-20220728192822612](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728192822612.png)

      5. 테스트가 통과되었으면 리팩토링해준다.

         ![83866bec-7892-4f6d-8117-b906cbc6f3ae](https://raw.githubusercontent.com/is3js/screenshots/main/83866bec-7892-4f6d-8117-b906cbc6f3ae.gif)

      6. **테스트에 도메인인자가 모두 사라졌다면, `오버로딩으로 내부 호출되는 도메인 파라미터 메서드는 private`화 해준다.**
         ![image-20220728193339887](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728193339887.png)

    - 서비스에서 원시값의 도메인 포장 로직이 반영되었다면, **도메인 자체의 검증Test도 시행해준다.**

      

12. **이후 `도메인 컬렉션 인자 포장`시 [이미 원시값 인자 존재 + 1개의 오버로딩 존재 + 도메인컬렉션 파라미터는 필요없을 때]**

    1. **public 원시값 컬렉션 파라미터 메서드**가 있는 상태에서
    2. 원시값 ->  도메인변환 -> **오버로딩 private도메인컬렉션 파라미터 메서드**
    3. **`오버로딩 private도메인 파라미터메서드만`수정대상으로 삼아 `2메서드`를 만들고**
       - **도메인 컬렉션 파라미터 -> 내부에서 `일급컬렉션 변환`후**
       - **`오버로딩 private일급컬렉션 파라미터 메서드` 대신, 내부에서 만든 `p->변환메서드->p2(일급컬렉션)` 자체를 `파라미터 추출`로 올려**
         - **도메인컬렉션 -> 일급으로 변환을 `오버로딩 내수용 메서드의 인자에서 호출`  + `내부는 변환로직의 return값인 p2(일급컬렉션)이 파라미터`가 되게 한다.**

13. 적용해보기

    1. 수정대상 메서드는 `private내수용 오버로딩 [도메인 컬렉션 파라미터] 메서드`이다.
       ![image-20220728222835121](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728222835121.png)

    2. 수정 대상을 2메서드로 복사한 뒤, **내수용 오버로딩 메서드라서 여기서 테스트는 못한다. `public 메서드로 테스트할껏이므로 상위메서드는 2메서드를 사용하도록 일단 바꾼다.`**

       ![36bae828-6526-4c07-ab14-0fa31695e773](https://raw.githubusercontent.com/is3js/screenshots/main/36bae828-6526-4c07-ab14-0fa31695e773.gif)

       ![image-20220728223012491](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728223012491.png)

    3. **2메서드의 파라미터를 변환하여 `파라미터가 되길 원하는 값을 return`하는 변환메서드(or 생성자호출)를 작성한다.**

       - p1 -> 변환메서드(p1) 추출 -> 예비 p2 상태에서

       - 변환메서드를 파라미터 추출하면

         - 외부메서드호출부( 변환메서드(p1) )  -> 내부는 (p2)가 파라미터가 된다.
         - **파라미터 변환 적용하기 작전이다.**

       - **변수명은 똑같이 추출해서, 잠시는 에러나더라도 `같은이름으로 변경하여, 내부로직에서 변경사항 파악이 쉽게 빨간줄`들어오게 해놔야한다.**

         ![b5faea6a-1923-4e92-9dd9-eff70abd30e6](https://raw.githubusercontent.com/is3js/screenshots/main/b5faea6a-1923-4e92-9dd9-eff70abd30e6.gif)

       ![image-20220728223805702](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728223805702.png)

       ![image-20220728223820394](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728223820394.png)

14. **파라미터 변환(도메인 컬렉션 -> 일급컬렉션) 변경에 따라 `컬렉션 에서 물어보던 것들을, 일급컬렉션 내부러 던져`야한다.**

    1. 일단은 빨간줄을 없애도록 작성한다.
    2. **파라미터 변경메서드로 기존테스트가 잘돌아가는지 확인한다.**

    ![936f41b6-5089-4147-8b5c-079a63aadd39](https://raw.githubusercontent.com/is3js/screenshots/main/936f41b6-5089-4147-8b5c-079a63aadd39.gif)

15. **일급컬렉션에 원하는 검증로직(중복검사 by distinct.count vs size, 갯수)를 확인한다. -> 예외발생 테스트 통과시 2메서드를 반영한다**

    ![image-20220728225112598](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728225112598.png)

16. **이제, 도메인 객체(Lotto, 일급컬렉션)의 경계값 테스트도 작성해야햔다.**

    1. **현재는 도메인 컬렉션 생성자만 있다. `원시값 컬렉션 파라미터 생성자`를 추가하고 싶다.**

       - **`파라미터 추가`는 `인자 그대로 작성후 -> 변환 -> 오버로딩`의 과정으로 추가한다고 했다.**

       ![537c2489-1865-4ba3-9f56-77bae17e7db9](https://raw.githubusercontent.com/is3js/screenshots/main/537c2489-1865-4ba3-9f56-77bae17e7db9.gif)

       - 만약, 원시값 배열로 입력하면 **가변인자(배열)로 받는다.**

       ![image-20220728233822464](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728233822464.png)

       ![image-20220728233846657](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728233846657.png)

       

17. **서비스 start~end까지 로직이 짜여졌으면 `해당 로직에 맞는 메서드명`으로 변경한다.**

    - start() -> match()

    ![image-20220729121535788](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729121535788.png)

18. **`서비스`내 `getter`가 보이면, `도메인 내부 로직`으로서 `캡슐화로 감춰야하는 로직`임을 100% 생각한다.**

    - 출력을 제외하고 getter는 없다고 보자.
    - **getter이후가 같은형의 비교면 -> `해당형으로 책임을 위임해 옮긴다`**
    - **getter이후가 다른형의 비교면 -> `제3형을 만들어 책임을 위임한다.`**
    - **같은형의 비교시 -> 내부 메서드로 돌아갈 때, `하나는 other라는 파라미터`명으로 잡아서 처리해준다.**
    - 일급컬렉션엔 **일반 컬렉션이 못했던 책임위임을 할 수있다.**
      - 내부에서 알아서 하도록 / 출력할때 빼곤, 객체에 getter를 쓰지 않고 위임한다.

19. **같은 형 2개의 비교로직 위임이면, `getter를 쓰는 하나만 타겟팅해서 위임받을 context`로 잡아야한다.**

    - **`위임받지 않는 녀석만 파라미터에 포함`되도록 하려면**

      1. static에서 **static으로추출된 상태로 옮기면 위임받는 객체가 this등으로 리팩토링 안되게 된다**.

      2. **객체에 위임하는 로직은 `메서드 추출후, static있다면 삭제`해야한다. `static내부 nonstatic메서드로 빨간줄이 떠도 참아야함`**

         - static은 공용, 상태없는 유틸메서드이므로.. 파라미터 input -> output형태라서 this등 리팩토링 안됨

         ![f4ac0669-9119-45b5-a82c-e1d1b2127774](https://raw.githubusercontent.com/is3js/screenshots/main/f4ac0669-9119-45b5-a82c-e1d1b2127774.gif)

         ![image-20220729123957410](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729123957410.png)

         ![image-20220729124009064](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729124009064.png)

      3. **`같은형의 객체 2개가 파라미터`에 있으면, `타겟팅할 변수를 선택`하라고 인텔리제이가 알려준다.**

         - **private변수를 객체에 위임할 땐, `Escalate -> Public`으로 바꿔서 이동시켜준다.**

         ![c158be1a-3e35-458c-9673-8d7055af69e6](https://raw.githubusercontent.com/is3js/screenshots/main/c158be1a-3e35-458c-9673-8d7055af69e6.gif)

         ![image-20220729124055503](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729124055503.png)


         ![image-20220729124113547](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729124113547.png)

         ![image-20220729124212538](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729124212538.png)

      4. **같은형의 비교에서 `파라미터에 있는 같은형은 other로 네이밍`해주자.**

         ![image-20220729124604547](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729124604547.png)

    - **메서드 추출로 위임되었으면 `getter()호출부를 삭제`한다.**

      ![3ae22d96-e1e6-46fd-b833-325f96165f2d](https://raw.githubusercontent.com/is3js/screenshots/main/3ae22d96-e1e6-46fd-b833-325f96165f2d.gif)



20. **제한된 종류의 상수가 보이면 `enum` 값객체로 대신할 수있다.**

    - **이 때, 네이밍은 `의미_원래값`형태로 해주면 된다.**
      - 1 -> RANK_1
      - 0 -> RANK_NONE

    ![image-20220729130130395](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729130130395.png)

    ![76f2edae-da52-492e-b13a-b344878608da](https://raw.githubusercontent.com/is3js/screenshots/main/76f2edae-da52-492e-b13a-b344878608da.gif)

    - 제한된 종류의 상수를 작성한 뒤 원래값을 매핑해둔다.

      ![de54771b-9bef-4ac2-b3ae-ddd9a5c8e7b0](https://raw.githubusercontent.com/is3js/screenshots/main/de54771b-9bef-4ac2-b3ae-ddd9a5c8e7b0.gif)

      ![image-20220729130728692](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729130728692.png)

      ![image-20220729130739063](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729130739063.png)

    - 응답값이 상수에서 -> 값객체enum으로 변경되었으니 테스트도 다 수정해준다.

      ![image-20220729131056477](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729131056477.png)

21. **enum은 **

    1. **`{}` : `값객체가 외부에서 파라미터로 입력`되면 `추상클래스로서 추상메서드를 이용해 [가상인자+람다식]에의해 수행되도록 전략객체로서 행위를 구현`해놓을 수 있지만, **
    2. **`()`: `분기별 값객체 반환`시  `분기문 자체를 값객체에 매핑`해놓고, `values()를 통해 매핑된 정보를 바탕으로 해당 값객체를 반환`해주는 `정적팩토리메서드`가 될 수 있다.**

22. **현재 분기별로 생성된다. -> `정적 팩토리 메서드로서 [분기를 함수형인터페이스로 매핑하여 돌면서 지연실행될] 생성메서드를 만들고 위임`해야한다.**

    - **기존 : 분기별 생성을 정적팩토리메서드에 위임하기 전 확인사항이 있다.**
      ![image-20220729135615639](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729135615639.png)

    1. **`전체 값객체(enum)생성 분기문을 위임`해야한다. 그전에 해야할 것이 있다.**

       1. 위임의 첫단추는 **`내부context로 사용하는 값은 위임객체에선 외부context`가 되도록 `위임객체context외에 모든 context값들을 변수로 만들어서 추출`**해야한다.

       2. **특히 파라미터가 제일 많은 부분을 확인해야하며, `조건식 내에서 메서드호출된 것도 값이다!!`**

          - **boolean문안에서 `여러객체를 이용한 메서드호출() -> 1개의 응답값`을 가지는 `값(파라미터) 1개`로서 -> `1개의 외부context로 위임`될 수 있도록 `추출될 로직보다 더 위쪽에 미리 1개의 지역변수로 빼놔야한다.`**

            ![image-20220729143012230](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729143012230.png)

          - **badCase: `로직 위임전 [내부 여러객체.메서드호출()]부를 지역변수로 안빼놨을 때`**

            - 여러객체를 사용한 메서드호출은 어차피 1개의 값으로 사용되는데, 연관된 객체가 모두 변수로 뽑힌다.

            ![a5704e03-7a3a-430e-bc57-6de5e94fd817](https://raw.githubusercontent.com/is3js/screenshots/main/a5704e03-7a3a-430e-bc57-6de5e94fd817.gif)
            ![image-20220729143737025](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729143737025.png)

          - **GoodCase: `로직 위임전 [내부 여러객체.메서드호출()]부를 위임로직 더 위쪽에 지역변수 1개로 응답값을 받았을 때`**

            ![710eccce-c554-4ce1-8a24-ab02c97ae2ff](https://raw.githubusercontent.com/is3js/screenshots/main/710eccce-c554-4ce1-8a24-ab02c97ae2ff.gif)

            ![image-20220729143915942](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729143915942.png)



23. 위임할 로직 전체보다 **더 위쪽에서, 위임 로직 내부 `객체.메서드호출()`부를 지역변수 1개로 빼놓고 메서드추출한다.**

    - **정팩메 위임의 메서드명은 `of`라고 지으면 된다.**

      ![8f0ec9a8-4ed4-426d-a87b-9906f425e56d](https://raw.githubusercontent.com/is3js/screenshots/main/8f0ec9a8-4ed4-426d-a87b-9906f425e56d.gif)

      ![image-20220729144338718](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729144338718.png)

24. 위임할 정팩메of를 enum에 위임한다.
    ![66394583-63e1-49ee-8775-e3d6b1f256dd](https://raw.githubusercontent.com/is3js/screenshots/main/66394583-63e1-49ee-8775-e3d6b1f256dd.gif)

25. **enum은 `내부 분기문`들을 `인스턴스에 ()매핑후 돌면서 찾기로 제거`가 가능하다.**

    1. **각 분기문들을 values() -> filter에 걸릴 수 있게 `각 인스턴스에 지연될 실행될 로직으로서 매핑`해야한다.**
       1. 지연실행될 로직은 **`함수형 인터페이스(or전략인페.전메())으로 지연호출부 정의 -> 가상인자 람다식에서 외부구현(or전략객체로 생성)`의 방법이 있다**

26. **지연실행될 로직은 전략패턴이 아니라면 `미리 시그니쳐가 정의된 boolean을 반환하는 Predicate함수형인터페이스`를 사용하여 정의한다.**

    1. **매핑될 boolean반환형 함수형인터페이스의 변수명**은 condition으로 편하게 지어주자.

    2. enum에 매핑되어있는 condition을 찾아서 .test()로 지연실행할 것이다.

    3. **이 때, `람다식의 가상인자로 구성되는 분기식에 쓰이는 실제 인자들`을 `지연실행 메서드`에 넣어줘야한다.**

       ![61772918-118d-4dd2-827f-5c5d5ca82cb5](https://raw.githubusercontent.com/is3js/screenshots/main/61772918-118d-4dd2-827f-5c5d5ca82cb5.gif)

       ![image-20220729145725742](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729145725742.png)



27. **매핑할 함수형인터페이스는, enum의 필드로 선언해줘야한다.**

    - 빨간줄 생성하면 **filter속 .test()로 사용될**것을 인식하여 **BiPredicate**변수로 만들어준다.

    ![fef3f129-0039-4a72-b3a9-f65123c7801b](https://raw.githubusercontent.com/is3js/screenshots/main/fef3f129-0039-4a72-b3a9-f65123c7801b.gif)

    ![image-20220729150010288](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729150010288.png)

28. 값매핑 필드 삭제, **`함형 필드는 생성자에서 추가`한 뒤 각 분기문들을 `가상인자 람다식으로 구현`해야한다.**

    - **이 때, 분기문이 없는 enum필드도 매핑되어야하므로 분기문을 만들어주고 매핑한다.**

    ![image-20220729152531939](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729152531939.png)

    - **Bi로 정의했기 때문에, 1개의 파라미터만 쓰더라도, 파라미터가 많은 것을 따라야한다.**

      ![b9714d6d-c5b4-4d6c-93a0-f49c5dac3019](https://raw.githubusercontent.com/is3js/screenshots/main/b9714d6d-c5b4-4d6c-93a0-f49c5dac3019.gif)

      ![image-20220729153240181](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729153240181.png)

      ![image-20220729153257506](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729153257506.png)

29. **enum도 메서드 생성시 도메인 테스트를 해줘야한다.**

    ![image-20220729213638417](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729213638417.png)

    ![image-20220729213656362](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220729213656362.png)



30. **정적팩토리메서드로 class를 `객체찍는템플릿`에서 `능동적인 객체관리자`로 승격시킨다.**

    - new 생성자 : **100% 객체를 생성해야함(캐싱못함)**
    - 정펙매 
      - 캐싱(재사용) 가능해짐.

    1. 인자의 갯수에 따라 .of or from으로 public static 메서드를 생성한다.

    2. **기존 생성자와 동일한 형으로 key / 재사용할 객체형을 value로 해서 HashMap을 만든다. / 캐싱할 인스턴스 객체수를 알고 있다면 capacity를 저적어준다.**

       - 변수는 static변수로서 **상수의 map으로서 jvm돌때 미리 생성된다.**

       ![fdf430f0-78d6-456b-8886-a66b363b0560](https://raw.githubusercontent.com/is3js/screenshots/main/fdf430f0-78d6-456b-8886-a66b363b0560.gif)

       ![image-20220731113819787](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731113819787.png)

    3. **캐싱의 핵심은, `존재하면 map에서 get`으로 꺼내고, `없으면 생성`이다**

       - **map의 초기화도 미리 이루어진다.**

       ![96c640ce-9600-4c59-ba85-20f1efb9ea44](https://raw.githubusercontent.com/is3js/screenshots/main/96c640ce-9600-4c59-ba85-20f1efb9ea44.gif)

       ![image-20220731114201232](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731114201232.png)

    4. **없으면 key-value 넣어주기, 있으면 꺼내기는 `map.computeIfAbsent( key, 람다식으로 value생성식)`을 넣어주면 된다.**

       - 람다식은 가상인자로 작성해야해서, key값을 그대로 넣어주면 에러남.

       ![e487c03a-c2da-4ec3-953c-e059eca42fb2](https://raw.githubusercontent.com/is3js/screenshots/main/e487c03a-c2da-4ec3-953c-e059eca42fb2.gif)

       ![image-20220731114834750](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731114834750.png)

    5. **정적팩토리메서드가 완성되었으면, `기본생성자는 private으로 막아`두고 `각종 검증방법은 정팩메로 이동`시킨다.**

       ![c5e8e02d-0fa3-4695-a519-216388391380](https://raw.githubusercontent.com/is3js/screenshots/main/c5e8e02d-0fa3-4695-a519-216388391380.gif)

       ![image-20220731115302033](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731115302033.png)

    6. **기존 new때려서 생성한 객체들을 of로 다 수정해준다.**

       ![image-20220731115746576](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731115746576.png)

    7. **캐싱의 테스트는 식별자 일치를 확인하는 `.isSameAs`를 사용해서 테스트한다.**

       ![a33cf917-9f91-4f90-9e2b-68e797d53184](https://raw.githubusercontent.com/is3js/screenshots/main/a33cf917-9f91-4f90-9e2b-68e797d53184.gif)

       ![image-20220731115026661](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731115026661.png)

    8. **여러 쓰레드에서 접근한다면, `미리 모두 static생성 + ConcurrentHashMap`으로 변경하고 getter만 하게 한다.**

       - **`static 컬렉션 변수`의 요소들은 `staitc블록으로 요소들 미리 생성`하게 할 수있다.**
       - static블럭이 늘어나면, 해석하는데 힘이쓰여 좋지 않ㄴ다.

       ![1acaf790-305c-4d20-a617-d25f7cc2352b](https://raw.githubusercontent.com/is3js/screenshots/main/1acaf790-305c-4d20-a617-d25f7cc2352b.gif)

       ![image-20220731120759176](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731120759176.png)

31. **생성할 수 있는 파라미터를 추가해 `생성자가 많아 견고한 클래스`를 만들 수 있다.**

    - **일반 생성자든 정펙매(캐싱 등) 생성자든 `정팩메or생성자에 또다른 파라미터를 추가`할 수 있다.**
    - 상위 파라미터(더 원시) 추가는 **변환 후 오버로딩**을 하면 된다고 했다.
      - 참고) 더 하위 파라미터를 추가해도 마찬가지지만, **`더 하위 파라미터(기존 파라미터 변환해서 만들어지는)로 변환`은 변환후 -> 메서드추출 -> 파라미터로 추출로 바깥에서 일하게 만들어서 파라미터를 바꾼다고 했음.**

    ![f63afa79-9d49-4db8-af83-ca4c73c4e9a9](https://raw.githubusercontent.com/is3js/screenshots/main/f63afa79-9d49-4db8-af83-ca4c73c4e9a9.gif)

    ![image-20220731152229083](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731152229083.png)



32. **불변 일급컬렉션을 만드는 과정**

    - **멋도 모르고 `컬렉션<단일객체>을 생성자에 통채로 집어넣어 만든 일급컬렉션은 `사실 `상태 변화(setter/add 등) -> [변화된 상태의 새 객체]를 생성해서 반환`하기 위해 생겨난 생성자임을 생각해야한다.**

    1. **단일객체에 대한 복수형으로 `final  불변의 일급컬렉션 클래스`를 생성하고, `Set으로 단일객체를 보유할 빈 컬렉션을 조합`한 필드를 가지고 있는다.**

       ![be034d02-8ef8-430e-a79b-aa6981b12351](https://raw.githubusercontent.com/is3js/screenshots/main/be034d02-8ef8-430e-a79b-aa6981b12351.gif)

       ![image-20220731155154252](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731155154252.png)

       

    2. **필드에 대해서 setter(상태변화) / getter를 고민해야한다.**

       - **단일객체가 `외부에서 변수로 받아져 조작되는 객체`라면, 객체 자체를 파라미터로 받는 setter로 만든다.**
       - **`컬렉션에 대한 setter는 add/remve등`이다. `add로 단일객체 파라미터`를 받되**
       - **상태변화의 setter(add)는 `상태변화된 새 객체를 반환`하여 불변성을 유지한다.**

       ![f8c3e381-7d52-40d3-ab9f-1de7c9415775](https://raw.githubusercontent.com/is3js/screenshots/main/f8c3e381-7d52-40d3-ab9f-1de7c9415775.gif)

       ![image-20220731155645815](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731155645815.png)

       1. **2번에서 `상태변화된 컬렉션으로 새 객체 생성해서 반환`을 하기 위해`컬렉션을 통째로 받아 생성하는 생성자`가 필요했음을 생각한다.**
       2. **즉, 필요에 의해 객체컬렉션을 받는 생성자가 생겼음을 인지한다.**

    3. **컬렉션 필드의 상태변화는 `기존 컬렉션필드가 변하지 않도록 사본으로 복사한 다음 상태변화`시킨 후 `새객체 생성`을 해줘야한다.**

       - **`컬렉션필드를 수정할 땐, 항상 사본복사후 연산`하자.**
         - **생성자를 통한 복사는 `객체요소는 같은 주소를 바라봐서 요소변화가 가능한`상태로 복사이다.**

       ![62548315-caf2-4b2b-826b-6cb4a66b516b](https://raw.githubusercontent.com/is3js/screenshots/main/62548315-caf2-4b2b-826b-6cb4a66b516b.gif)

       ![image-20220731162206685](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731162206685.png)

    4. **기본생성자외 `컬렉션 필드의 생성자가 생성되는 순간, 빈컬렉션의 필드초기화`는 의미가 없어진다. `최초의 시작은 빈컬렉션으로 될 수 있도록 부생성자로서 추가`해줘야한다.**

       ![70742dde-326d-48ef-9798-c10674e600c2](https://raw.githubusercontent.com/is3js/screenshots/main/70742dde-326d-48ef-9798-c10674e600c2.gif)

       ![image-20220731161528409](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731161528409.png)

    5. **파라미터가 없는 생성자는 `부생성자`이므로 `파라미터가 있는 주생성자를 this로 사용`해줘야한다.**

       ![7d75e887-f5ac-44ee-b68a-2ea0f0745aaf](https://raw.githubusercontent.com/is3js/screenshots/main/7d75e887-f5ac-44ee-b68a-2ea0f0745aaf.gif)

       ![image-20220731161701801](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731161701801.png)

    6. **불변의 일급컬렉션에 요소를 add하는 과정은 매번 재할당해줘야한다.**

       - **불변 객체를 받는 `변수`**는 재할당이 운명이다.

       - **일급컬렉션은 getter().size()가 아니라 `.size()`메서드를 정의해서 쓴다.**

       ![image-20220731163156321](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220731163156321.png)

    

    

​	



#### 참고스샷

- [최범균 블로그? 배민 팀장 블로그](https://blog.kingbbode.com/52?category=737337)

![image-20220728172531672](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728172531672.png)





## 내부의 테스트 제어가 안되는 코드(랜덤, 날짜)는 [해당로직을 포함한 메서드]로 추출하고 전략객체.전략메서드()에 위임하고 외부에서 로직을 전달할 수 있게 하여, [테스트에 용이한 다른 전략]으로서 [랜덤 대신 확정값을 가상인자+람다식으로 구현]하여 전략메서드를 구현한다.



### (1) [자경] 

![image-20220728015130242](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728015130242.png)

#### 메서드내 랜덤로직이 있어서 테스트가 곤란한 경우

![image-20220728005109597](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728005109597.png)



1. 전략객체에 위임할 로직을 **(예비 전략)private메서드로 추출**한다.
   ![3e511260-b29a-4cc7-bc03-100d147b2577](https://raw.githubusercontent.com/is3js/screenshots/main/3e511260-b29a-4cc7-bc03-100d147b2577.gif)

2. **`추출한(위임할) 로직`은 `전략메서드 구현`이므로 `구상 전략객체`로 넘겨야한다. **

   1. **`인터페이스 생성 -> `**
   2. **`구상체 생성 -> 위임 메서드 복붙 -> `**
   3. **`전략 인터페이스로 pull up`해서 올려보자.**

   ![2de82b7b-aaf2-4ee8-b24f-495de845fca6](https://raw.githubusercontent.com/is3js/screenshots/main/2de82b7b-aaf2-4ee8-b24f-495de845fca6.gif)

3. **전략객체는 협력객체로서 메서드인자가 아닌 `생성자 주입되는 소유모델`이 되어야한다.**

   - **파라미터에서 전략객체를 받지말고, 생성자에서 받자.**

   ![d57d101a-f9d5-4d9d-84c5-c581532c329a](https://raw.githubusercontent.com/is3js/screenshots/main/d57d101a-f9d5-4d9d-84c5-c581532c329a.gif)

4. **`인자로 들어가는 [협력객체]가 [Product용 기본값]이 있는 경우`, `오버로딩을 이용해서 내부에서 협력객체 기본값`을 강제시킬 수 있다.**

   ​	![image-20220728012032366](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728012032366.png)

5. **이제 테스트코드에서는 랜덤전략 대신 `실시간 확정 전략`을 주입하자.**

   - **전략객체.전략메서드()는 1개의 오퍼레이터만 가지는 함수형인터페이스로서 `전략메서드를 구상전략객체 없이, (내부 추상체.전략메서드()부분을) 가상인자와 람다식을 통한 실시간 구현`이 가능하다**

     ![837ab787-ffd7-4e42-a896-2decc31bec86](https://raw.githubusercontent.com/is3js/screenshots/main/837ab787-ffd7-4e42-a896-2decc31bec86.gif)

     ![image-20220728011520268](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728011520268.png)

   - 필요하다면, **전략메서드에 확정전략을 구현하는 구상전략객체를 추가해서 대입해줘도 된다.**

     - 실시간 구현이 어렵거나, 테스트용 람다식이 아닌 구상체를 선택해야할 경우

6. **상태를 변화시키는 메서드가, `테스트를 위해 상수 응답`하는 `getter의 역할`까지 하고 있으므로 2메서드를 만들고, return상수응답 로직을 제거  뒤, `테스트에서 객체의 변화된 상태로 비교`할 수 있게 수정해본다.**

   - **`eq/hC재정의 후 객체 비교` vs `getter구현하여 해당 상태값만 비교`할 수 있다.**

     - **현재는 전략객체 또한 필드로 들고 있으므로 `getter를 따로 생성하여 상태 비교`를 하자**

     ![15b8da34-a0c8-47d9-92fa-82bc36933635](https://raw.githubusercontent.com/is3js/screenshots/main/15b8da34-a0c8-47d9-92fa-82bc36933635.gif)

   - 비교가 잘되면, **기존 테스드들 `원본메서드 -> 2메서드로 수정` + `응답값비교 -> 객체상태값비교`로 모두 수정하고 `원본 삭제후 2메서드를 원본으로 네이밍`한다.**



7. **상태변화 메서드인 .move()는 `setter의 상태변화 대신, 상태변화된 객체를 반환하는 메서드`로 변경한다.**

   1. **`객체 자체를 수정`하는 것이므로 `2객체`를 만들어서 테스트해야한다.**

   2. move2를 만들고 **position++처럼 상태를 변화하는 부분에서 `상태변화된 필드로 새 객체를 만들어 반환`하도록 수정한다.**

      ![image-20220728024413514](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728024413514.png)

   3. **상태변화하는 필드는 기본값을 주고 생성자에서 안받았기 때문에, `생성자를 추가`해주고 `필드에는 final을 붙여준다.`**

      ![8c73c079-8872-4d88-8284-f7385973688f](https://raw.githubusercontent.com/is3js/screenshots/main/8c73c079-8872-4d88-8284-f7385973688f.gif)

      ![image-20220728024834800](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728024834800.png)

   4. **테스트에서는 `자신형 반환 메서드는 체이닝`이 가능하므로 체이닝으로 작성해주면 된다.**

      - 현재 객체는 협력객체를 포함하고 있어서, **객체끼리 비교가 불가하므로 어쩔 수 없이 getter로 필드를 확인한다.**

      ![image-20220728025404493](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728025404493.png)

   5. **기존의 테스트들(동일 객체 상태비교)을 새 객체반환을 변수로 받아서 상태를 확인하도록 변경한다.**

      - 이후, Car의 기존이름을 바꾸고, Car2 -> Car로 변경한다.

8. **상태변경 메서드(새 객체반환)의 테스트가 끝났으면, `해당 상태로 만들어주는 생성자 추가`를고려해서, 다른 테스트에 용이하게 한다.**

   ![image-20220728122259735](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728122259735.png)

   - position 3과 5에 **상태로 바로 도달하게끔 생성자를 추가해서, 이미 확정된 객체를 만든다.**

     - **이렇게 하면,빈컬렉션+add로직이  컬렉션.of(특정상태,객체들)로 변경된다.**

     ![image-20220728122524465](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728122524465.png)

     ![image-20220728122511234](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728122511234.png)

     ![image-20220728122624569](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728122624569.png)

#### 참고 스샷

##### 자동차경주-테스트에서 서비스주입



![image-20220728030339975](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728030339975.png)

##### 자동차경주-service로부터 raw결과(list)를 반환시 반환형 ResultView

![image-20220728030702845](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728030702845.png)

![image-20220728030832105](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728030832105.png)

![image-20220728031822882](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728031822882.png)

![image-20220728031757141](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728031757141.png)

![image-20220728031850384](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220728031850384.png)



##### 정리 md(맷)

- [마크다운](https://github.com/Hyunta/WoowaCourse/blob/0e31e930cd227ce6faef8a3d1db55d0c18345455/docs/lv1/%EC%9E%90%EB%8F%99%EC%B0%A8%20%EA%B2%BD%EC%A3%BC%20%EB%AF%B8%EC%85%98/%EC%9E%90%EB%8F%99%EC%B0%A8%20%EA%B2%BD%EC%A3%BC%20%ED%94%BC%EB%93%9C%EB%B0%B1.md)

#### 정리

- service는 inputview를 알아도 되고, resultview를 반환해도 된다.
- resultview는 출력을 하기 위해 도메인객체를 input으로 받고, 출력관련 책임만 가진다.
  - 실제 print는 outputview가 하고, resultview는 출력될 요소를 만드는 역할이다.
  - resultview는 유틸메서드로서 string을 반환해준다.
  - racingGame -> resultview에 전달할때는, car list를 반환해주고, **실제 list에서 문자열을 뽑아주는 기능을 사용하는 `resultview내부에서 일급컬렉션을 의존사용`하고 `외부에서 일급컬렉션 생성가능한 파라미터로는 객체가 아닌 재료만`받는다.**
    - 객체로 메세지를 전달하지만,  1개 객체의 add(create)에서는 재료만받을 수도 있다(객체 조작을 외부에서 꺼내서못하게 할때)
    - **객체list도 객체라서 굳이 일급컬렉션을 외부에서 사용안할때 생성할필요는 없다?**



