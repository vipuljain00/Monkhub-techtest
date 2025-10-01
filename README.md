## API Endpoints

### Customers

- **GET /api/customers**: Retrieve all customers.
- **POST /api/customers**: Create a new customer.


### Discount Coupons

- **GET /api/discount-coupons**: Retrieve all discount coupons.
- **GET /api/discount-coupons/{id}**: Retrieve a discount coupon by ID.
- **POST /api/discount-coupons**: Create a new discount coupon.
  - Request Body: JSON object with coupon details (e.g., `title`, `couponCode`, `discountType`, `discountValue`, `expiryDate`, `customerScope`, `customerIds` if applicable).
- **PUT /api/discount-coupons/{id}**: Update an existing discount coupon.
  - Request Body: JSON object with updated coupon details.
- **DELETE /api/discount-coupons/{id}**: Delete a discount coupon by ID.

## Sample Requests

### Create Customer

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "mobile": "1234567890"
}
```

### Create Discount Coupon

```json
{
  "title": "Summer Sale",
  "couponCode": "SUMMER2023",
  "discountType": "percentage",
  "discountValue": 20.0,
  "expiryDate": "2023-12-31",
  "customerScope": "selected",
  "customerIds": [1, 2],
  "minimumDiscount": 10.0,
  "maximumDiscount": 100.0,
  "usagePerCustomer": "unlimited",
  "usageLimit": 100
}
```

## Database

The application uses an embedded H2 database. You can access the H2 console at `http://localhost:8080/h2-console` with the following credentials:

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

## Testing

Run the tests using Maven:

```bash
mvn test
```

## Contributing

1. Fork the repository.
2. Create a feature branch.
3. Commit your changes.
4. Push to the branch.
5. Create a Pull Request.

## License

This project is licensed under the MIT License.
