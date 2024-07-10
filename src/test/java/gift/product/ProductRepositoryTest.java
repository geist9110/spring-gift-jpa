package gift.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE product RESTART IDENTITY");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Test
    @DisplayName("[Unit] addProduct test")
    void addProduct() {
        // given
        Product expected = new Product(-1L, "product-1", 100, "product-image-url-1");

        //when
        productRepository.save(expected);
        Product actual = productRepository.findById(1L).get();

        //then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(1L),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    @DisplayName("[Unit] getAllProducts test")
    void getAllProducts() {
        //given
        List<Product> expected = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Product product = new Product(i, "product-" + i, i * 100, "product-image-url-" + i);
            productRepository.save(product);
            expected.add(product);
        }

        //when
        List<Product> actual = productRepository.findAll();

        //that
        assertAll(
            () -> assertThat(actual.get(0)).isEqualTo(expected.get(0)),
            () -> assertThat(actual.get(1)).isEqualTo(expected.get(1)),
            () -> assertThat(actual.get(2)).isEqualTo(expected.get(2))
        );
    }

    @Test
    @DisplayName("[Unit] updateProduct test")
    void updateProductTest() {
        //given
        productRepository.save(new Product(-1L, "product-1", 100, "product-image-url-1"));
        Product expected = new Product(1, "product-2", 200, "product-image-url-2");

        //when
        productRepository.save(expected);
        Product actual = productRepository.findById(1L).get();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("[Unit] deleteProduct test")
    void deleteProductTest() {
        //given
        productRepository.save(new Product(-1L, "product-1", 100, "product-image-url-1"));

        //when
        productRepository.deleteById(1L);
        boolean actual = productRepository.existsById(1L);

        //then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("[Unit] existProduct test")
    void existProduct() {
        //given
        Product expect = new Product(1L, "product-1", 100, "product-image-url-1");
        productRepository.save(expect);

        //when
        boolean trueCase = productRepository.existsById(1L);
        boolean falseCase = productRepository.existsById(2L);

        //then
        assertAll(
            () -> assertThat(trueCase).isTrue(),
            () -> assertThat(falseCase).isFalse()
        );
    }
}