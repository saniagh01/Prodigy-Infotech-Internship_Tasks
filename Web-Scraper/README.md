
# 🛒 E-Commerce Web Scraper

A Python-based web scraping application that extracts product information from an e-commerce website and stores the collected data in a structured CSV file.

This project was developed as **Task-05** of my Software Development Internship at **Prodigy Infotech**.

---

## 📌 Project Overview

The E-Commerce Web Scraper automatically collects product information from the practice e-commerce website **Books to Scrape**.

The scraper navigates through multiple pages, extracts relevant product details, cleans the collected information, and exports the final dataset into a CSV file.

---

## ✨ Features

- 🌐 Scrapes products from an e-commerce website
- 📄 Supports multi-page scraping
- 🏷️ Extracts product names
- 💰 Extracts product prices
- ⭐ Extracts product ratings
- 📦 Extracts product availability
- 🔗 Extracts product URLs
- 🧹 Cleans and standardizes collected data
- 🔄 Removes duplicate products
- 📊 Exports data into CSV format
- 📝 Maintains a scraping log
- ⚠️ Includes error handling for failed requests
- ⏱️ Adds a delay between requests to avoid excessive server requests

---

## 🛠️ Technologies Used

- **Python**
- **Requests**
- **BeautifulSoup**
- **Pandas**
- **CSV**
- **Logging**

---

## 📂 Project Structure

```text
E-Commerce-Web-Scraper/
│
├── scraper.py
├── requirements.txt
├── README.md
├── .gitignore
│
├── data/
│   └── products.csv
│
└── logs/
    └── scraper.log
    