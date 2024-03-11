package bootiful.jdbc

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.ListCrudRepository

@SpringBootApplication
class JdbcApplication {
  @Bean
  fun applicationRunner(repository: CustomerRepository) = ApplicationRunner {
    val order1 = Order(null, "One Big Cup of Coffee")
    val order2 = Order(null, "One Big Cup of Tea")
    repository.save(Customer(null, "Alice", Profile(null, "alice", "password"), setOf(order1, order2)))
    println(repository.findCustomerByName("Alice"))
  }
}

fun main(args: Array<String>) {
  runApplication<JdbcApplication>(*args)
}

data class Customer(@Id val id: Long?, val name: String, val profile: Profile, val orders: Set<Order>)

@Table("customer_order")
data class Order(@Id val id: Long?, val name: String)

@Table("customer_profile")
data class Profile(@Id val id: Long?, val username: String, val password: String)


interface CustomerRepository : ListCrudRepository<Customer, Long> {
  fun findCustomerByName(name: String): Customer
}
