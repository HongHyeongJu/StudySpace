## 인텔리제이에서 제공하는 각 Java 버전별 주요 기능과 키워드

<br>

### 1.4 - `'assert' keyword`
- **assert 키워드**: 조건이 참인지 확인하고, 그렇지 않은 경우 오류를 발생시킵니다. 주로 디버깅 목적으로 사용됩니다.

### 5 - `'enum' keyword, generics, autoboxing etc.`
- **`enum` 키워드**: 열거형 타입을 정의하는 키워드입니다. 제한된 개수의 상수 값을 갖는 타입을 정의할 때 사용합니다.
- **제네릭스 (Generics)**: 클래스와 메서드에 타입 파라미터를 추가할 수 있게 하여, 컴파일 타임에 타입 안전성을 제공합니다.
- **자동 박싱/언박싱 (Autoboxing/Unboxing)**: 기본 타입과 해당 래퍼 클래스 간의 자동 변환을 지원합니다. 예를 들어, `int`를 `Integer`로 자동 변환하거나 그 반대의 변환을 지원합니다.
- **가변 인자 (Varargs)**: 메서드에서 가변 인자의 사용을 허용하여, 호출 시 다양한 수의 인자를 전달할 수 있게 합니다.

### 6 - `@Override in interfaces`
- **`@Override` 어노테이션**: 메서드가 슈퍼클래스 또는 인터페이스의 메서드를 오버라이드하고 있음을 명시합니다. 잘못된 오버라이드를 방지하는 데 도움이 됩니다.

### 7 - `Diamonds, ARM, multi-catch etc.`
- **다이아몬드 연산자 (`<>`)**: 제네릭 클래스의 인스턴스를 생성할 때, 타입 파라미터를 생략할 수 있습니다.
- **자동 자원 관리 (Automatic Resource Management, ARM)**: `try-with-resources` 문을 도입하여, 자원(예: 파일, 네트워크 소켓) 관리를 더 간편하게 처리할 수 있습니다.
- **멀티 캐치 (Multi-catch)**: 단일 `catch` 블록에서 여러 예외를 한 번에 처리할 수 있도록 허용합니다.

### 8 - `Lambdas, type annotations etc.`
- **람다 표현식**: 익명 함수(함수형 프로그래밍 개념)를 더 간결하게 작성할 수 있습니다. 주로 스트림 API와 함께 사용됩니다.
- **메서드 참조**: 기존 메서드를 람다 표현식 대신 사용할 수 있게 합니다.
- **디폴트 메서드**: 인터페이스에서 메서드 구현을 제공할 수 있습니다.
- **타입 어노테이션**: 타입 선언부에 어노테이션을 사용할 수 있게 하여, 더 정교한 검사와 도구 지원이 가능합니다.
- **스트림 API**: 컬렉션을 함수형 스타일로 처리할 수 있는 강력한 API를 제공합니다.

### 9 - `Modules, private methods in interfaces etc.`
- **모듈 시스템 (Modules)**: 자바 애플리케이션을 모듈 단위로 구성할 수 있게 하여, 코드의 캡슐화와 재사용성을 향상시킵니다.
- **인터페이스 내 private 메서드**: 인터페이스 내에서 private 메서드를 정의하여, 인터페이스의 코드 재사용성을 높일 수 있습니다.

### 10 - `Local variable type inference`
- **지역 변수 타입 추론 (`var`)**: `var` 키워드를 사용하여, 변수 선언 시 타입을 생략할 수 있습니다. 컴파일러가 자동으로 타입을 추론합니다.

### 11 - `No new language features`
- **주요 언어 기능 추가 없음**: Java 11에서는 새로운 언어 기능이 추가되지 않았습니다. 대신 여러 API와 JVM 개선이 이루어졌습니다.

### 12 - `No new language features`
- **주요 언어 기능 추가 없음**: Java 12에서도 새로운 언어 기능이 추가되지 않았습니다.

### 13 - `No new language features`
- **주요 언어 기능 추가 없음**: Java 13에서도 새로운 언어 기능이 추가되지 않았습니다.

### 14 - `Switch expressions`
- **스위치 표현식 (Switch Expressions)**: `switch` 구문을 표현식으로 사용할 수 있게 하여, 간결하고 안전한 코드 작성이 가능합니다. 결과값을 반환할 수 있으며, `break` 대신 `yield` 키워드를 사용합니다.

### 15 - `Text blocks`
- **텍스트 블록 (Text Blocks)**: 여러 줄의 문자열을 더 쉽게 작성할 수 있게 하는 기능입니다. 큰따옴표 세 개(`"""`)로 감싸서 표현합니다.

### 16 - `Records, patterns, local enums and interfaces`
- **레코드 (Records)**: 불변 데이터 클래스를 간단하게 정의할 수 있는 구조를 제공합니다.
- **패턴 매칭 (Pattern Matching)**: `instanceof`와 함께 사용할 수 있는 패턴 매칭을 도입하여, 더 간결한 타입 캐스팅이 가능합니다.
- **로컬 열거형 및 인터페이스**: 로컬 클래스와 유사하게 로컬 열거형과 인터페이스를 선언할 수 있습니다.

### 17 - `Sealed types, always-strict floating-point semantics`
- **시일드 클래스 (Sealed Classes)**: 클래스 계층 구조를 제한하여, 특정 클래스만 상속할 수 있도록 제어합니다.
- **항상 엄격한 부동소수점 연산 (Always-Strict Floating-Point Semantics)**: 부동소수점 연산이 항상 엄격하게 수행되도록 합니다.

### 18 - `JavaDoc snippets`
- **JavaDoc 스니펫**: JavaDoc에서 코드 스니펫을 포함할 수 있는 기능을 추가하여, 문서화의 유연성을 높입니다.

### 19 - `No new language features`
- **주요 언어 기능 추가 없음**: Java 19에서는 새로운 언어 기능이 추가되지 않았습니다.

### 20 - `No new language features`
- **주요 언어 기능 추가 없음**: Java 20에서도 새로운 언어 기능이 추가되지 않았습니다.

### 21 - `Record patterns, pattern matching for switch`
- **레코드 패턴 (Record Patterns)**: 패턴 매칭의 범위를 레코드 타입으로 확장합니다.
- **스위치를 위한 패턴 매칭**: `switch` 구문에서 패턴 매칭을 사용하여 더 복잡한 분기 로직을 간결하게 작성할 수 있습니다.
