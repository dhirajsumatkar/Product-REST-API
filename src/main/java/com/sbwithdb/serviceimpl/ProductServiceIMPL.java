package com.sbwithdb.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbwithdb.dao.ProductDao;
import com.sbwithdb.model.Category;
import com.sbwithdb.model.Charges;
import com.sbwithdb.model.FinalProduct;
import com.sbwithdb.model.Product;
import com.sbwithdb.model.Supplier;
import com.sbwithdb.service.ProductService;
import com.sbwithdb.utility.ValidateProduct;

@Service
public class ProductServiceIMPL implements ProductService {

	@Autowired
	ProductDao dao;

	@Autowired
	private ValidateProduct validate;

	Map<String, String> isValid = new HashMap<>();

	Map<String, Map<String, String>> map = new HashMap<>();

	Map<String, Object> originalMap = new LinkedHashMap<String, Object>();

	List<Integer> existRowNum = new ArrayList<>();

	
	Integer existRecord = 0;
	Integer excludeRecord = 0;

	@Override
	public boolean saveProduct(Product product) {

		String productId = new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date());
		product.setProductId(Long.parseLong(productId));

		boolean isAdded = dao.saveProduct(product);
		return isAdded;
	}

	@Override
	public List<Product> getAllProducts() {

		List<Product> list = dao.getAllProducts();
		return list;

	}

	@Override
	public boolean updateProduct(Product product) {

		boolean isUpdated = dao.updateProduct(product);
		return isUpdated;
	}

	@Override
	public boolean deleteProduct(Long productId) {
		boolean isDeleted = dao.deleteProduct(productId);
		return isDeleted;
	}

	@Override
	public Product getProductById(Long productId) {

		return dao.getProductById(productId);
	}

	@Override
	public List<Product> sortProducts(String sortBy, String fieldName) {
		List<Product> list = dao.sortProducts(sortBy, fieldName);
		return list;
	}

	@Override
	public List<Product> getMaxPriceProducts() {
		List<Product> list = dao.getMaxPriceProducts();
		return list;
	}

	@Override
	public Double countSumOfProductPrice() {
		Double productPriceSum = dao.countSumOfProductPrice();
		return productPriceSum;
	}

	@Override
	public Long getTotalCountOfProducts() {

		Long count = dao.getTotalCountOfProducts();
		return count;
	}

	@Override
	public Product getProductByName(String getProductByName) {

		Product product = dao.getProductByName(getProductByName);

		return product;
	}

	public List<Product> readExcel(String filePath) {
		List<Product> list = new ArrayList<Product>();
		Integer totalRecord = 0;
		excludeRecord=0;
		try {

			// FileInputStream fileInputStream=new FileInputStream(filePath);

			// Workbook workbook=new XSSFWorkbook(fileInputStream);

			Workbook workbook = new XSSFWorkbook(filePath);

			Sheet sheet = workbook.getSheet("products");

			Iterator<Row> rows = sheet.rowIterator();

			while (rows.hasNext()) {
				Row row = (Row) rows.next();
				// exclude header row
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					continue;
				}

				totalRecord = totalRecord + 1;

				Product product = new Product();
				String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());

				// String newId=id.concat(String.valueOf(rowNum));

				Random random = new Random();
				String numbers = "123456789";
				char[] otp = new char[2];

				for (int i = 0; i < 2; i++) {

					otp[i] = numbers.charAt(random.nextInt(numbers.length()));
				}

				int number = Integer.parseInt(new String(otp));

				product.setProductId(Long.parseLong(id.concat(String.valueOf(number))));

				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = (Cell) cells.next();

					int columnIndex = cell.getColumnIndex();

					switch (columnIndex) {
					case 0: {
						product.setProductName(cell.getStringCellValue());
						break;
					}

					case 1: {
						Supplier supplier = new Supplier();
						supplier.setSupplierId((long) cell.getNumericCellValue());
						product.setSupplierId(supplier);
						break;
					}

					case 2: {
						Category category = new Category();
						category.setCategoryId((long) cell.getNumericCellValue());
						product.setCategoryId(category);
						break;
					}

					case 3: {
						product.setProductQty((int) cell.getNumericCellValue());

						break;
					}
					case 4: {
						product.setProductPrice(cell.getNumericCellValue());

						break;
					}

					}

				}

				isValid = validate.check(product);

				if (isValid.isEmpty()) {

					Product prod = dao.getProductByName(product.getProductName());

					if (prod == null) {
						list.add(product);
					} else {
						existRowNum.add(row.getRowNum()+1);
						existRecord = existRecord + 1;
					}

				} else {
					excludeRecord = excludeRecord + 1;
					map.put(String.valueOf(row.getRowNum()+1), isValid);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		originalMap.put("Total Records in Sheet", totalRecord);

		return list;

	}

	@Override
	public Map<String, Object> uploadSheet(MultipartFile file) {
		String path = "src/main/resources/";
		String name = file.getOriginalFilename();

		Integer addedCount = 0;

		try {

			FileOutputStream fos = new FileOutputStream(path + File.separator + name);

			byte[] data = file.getBytes();
			fos.write(data);

			List<Product> list = readExcel(path + File.separator + name);

			for (Product product : list) {
				boolean isSaved = dao.saveProduct(product);

				if (isSaved) {
					addedCount = addedCount + 1;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		originalMap.put("Total Records in Sheet", totalRecord);
		originalMap.put("Uploaded Records in DB ", addedCount);
		originalMap.put("Total exists Records in DB ", existRecord);
		originalMap.put("Row Num,Exists Record in DB", existRowNum);
		originalMap.put("Total excluded", excludeRecord);
		originalMap.put("Bad records row number", map);

		return originalMap;
	}

	@Override
	public FinalProduct getFinalProductById(Long productId) {

		Product product = dao.getProductById(productId);

		FinalProduct fp = new FinalProduct();

		if (product != null) {

			Charges charges = new Charges();

			double gstCharges = product.getProductPrice() * product.getCategoryId().getGst() / 100;
			System.out.println(gstCharges);

			double discount = product.getProductPrice() * product.getCategoryId().getDiscount() / 100;

			double finalProductPrice = product.getProductPrice() + gstCharges
					+ product.getCategoryId().getDeliveryCharge() + discount;

			charges.setGstCharge(gstCharges);
			charges.setDeliveryCharge(product.getCategoryId().getDeliveryCharge());
			charges.setDiscountAmount(discount);

			fp.setProductId(productId);
			fp.setProductName(product.getProductName());
			fp.setCategoryId(product.getCategoryId());
			fp.setSupplierId(product.getSupplierId());
			fp.setProductQty(product.getProductQty());
			fp.setProductPrice(product.getProductPrice());
			fp.setCharges(charges);
			fp.setFinalProductPrice(finalProductPrice);

		}

		return fp;
	}

}
