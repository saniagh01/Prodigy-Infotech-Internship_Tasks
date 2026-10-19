
import os
import time
import logging
import requests
import pandas as pd

from bs4 import BeautifulSoup
from urllib.parse import urljoin


# ============================================================
# CONFIGURATION
# ============================================================

BASE_URL = "https://books.toscrape.com/"
START_URL = BASE_URL

OUTPUT_FILE = "data/products.csv"
LOG_FILE = "logs/scraper.log"

REQUEST_TIMEOUT = 10
DELAY_BETWEEN_REQUESTS = 1


# ============================================================
# LOGGING SETUP
# ============================================================

os.makedirs("data", exist_ok=True)
os.makedirs("logs", exist_ok=True)

logging.basicConfig(
    filename=LOG_FILE,
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

logger = logging.getLogger(__name__)


# ============================================================
# HTTP SESSION
# ============================================================

session = requests.Session()

session.headers.update({
    "User-Agent": (
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
        "AppleWebKit/537.36 "
        "(KHTML, like Gecko) "
        "Chrome/139.0.0.0 Safari/537.36"
    )
})


# ============================================================
# FETCH PAGE
# ============================================================

def fetch_page(url):
    """
    Fetch HTML content from a URL.
    """

    try:
        response = session.get(
            url,
            timeout=REQUEST_TIMEOUT
        )

        response.raise_for_status()

        logger.info(f"Successfully fetched: {url}")

        return response.text

    except requests.RequestException as error:

        logger.error(
            f"Failed to fetch {url} | Error: {error}"
        )

        return None


# ============================================================
# EXTRACT RATING
# ============================================================

def extract_rating(product):

    rating_element = product.select_one(
        ".star-rating"
    )

    if not rating_element:
        return "Unknown"

    rating_classes = rating_element.get("class", [])

    rating_mapping = {
        "One": 1,
        "Two": 2,
        "Three": 3,
        "Four": 4,
        "Five": 5
    }

    for rating_name, rating_value in rating_mapping.items():

        if rating_name in rating_classes:
            return rating_value

    return "Unknown"


# ============================================================
# SCRAPE PRODUCTS FROM PAGE
# ============================================================

def scrape_products(html, current_url):

    soup = BeautifulSoup(
        html,
        "html.parser"
    )

    products = []

    product_cards = soup.select(
        "article.product_pod"
    )

    logger.info(
        f"Found {len(product_cards)} products on {current_url}"
    )

    for product in product_cards:

        # ----------------------------------------------------
        # PRODUCT NAME
        # ----------------------------------------------------

        title_element = product.select_one(
            "h3 a"
        )

        title = (
            title_element.get("title", "").strip()
            if title_element
            else "Unknown"
        )

        # ----------------------------------------------------
        # PRODUCT URL
        # ----------------------------------------------------

        product_url = ""

        if title_element:

            relative_url = title_element.get(
                "href",
                ""
            )

            product_url = urljoin(
                current_url,
                relative_url
            )

        # ----------------------------------------------------
        # PRICE
        # ----------------------------------------------------

        price_element = product.select_one(
            ".price_color"
        )

        price = (
            price_element.text.strip()
            if price_element
            else "Unknown"
        )

        # ----------------------------------------------------
        # AVAILABILITY
        # ----------------------------------------------------

        availability_element = product.select_one(
            ".availability"
        )

        availability = (
            " ".join(
                availability_element.stripped_strings
            )
            if availability_element
            else "Unknown"
        )

        # ----------------------------------------------------
        # RATING
        # ----------------------------------------------------

        rating = extract_rating(product)

        # ----------------------------------------------------
        # STORE DATA
        # ----------------------------------------------------

        products.append({
            "Product Name": title,
            "Price": price,
            "Rating": rating,
            "Availability": availability,
            "Product URL": product_url
        })

    return products


# ============================================================
# FIND NEXT PAGE
# ============================================================

def get_next_page(html, current_url):

    soup = BeautifulSoup(
        html,
        "html.parser"
    )

    next_button = soup.select_one(
        "li.next a"
    )

    if not next_button:
        return None

    next_url = next_button.get("href")

    if not next_url:
        return None

    return urljoin(
        current_url,
        next_url
    )


# ============================================================
# MAIN SCRAPER
# ============================================================

def scrape_website():

    all_products = []

    current_url = START_URL

    page_number = 1

    logger.info(
        "========== SCRAPING STARTED =========="
    )

    while current_url:

        print(
            f"Scraping page {page_number}..."
        )

        logger.info(
            f"Scraping page {page_number}: {current_url}"
        )

        html = fetch_page(current_url)

        if html is None:

            print(
                f"Could not fetch page {page_number}."
            )

            break

        products = scrape_products(
            html,
            current_url
        )

        all_products.extend(products)

        print(
            f"Found {len(products)} products."
        )

        current_url = get_next_page(
            html,
            current_url
        )

        page_number += 1

        if current_url:
            time.sleep(
                DELAY_BETWEEN_REQUESTS
            )

    return all_products


# ============================================================
# CLEAN DATA
# ============================================================

def clean_data(products):

    df = pd.DataFrame(products)

    if df.empty:
        return df

    # Remove duplicate products
    df.drop_duplicates(
        subset=["Product URL"],
        inplace=True
    )

    # Remove extra whitespace
    df["Product Name"] = (
        df["Product Name"]
        .astype(str)
        .str.strip()
    )

    df["Availability"] = (
        df["Availability"]
        .astype(str)
        .str.strip()
    )

    # Convert price into numeric value
    df["Price"] = (
        df["Price"]
        .astype(str)
        .str.replace("£", "", regex=False)
        .str.strip()
    )

    df["Price"] = pd.to_numeric(
        df["Price"],
        errors="coerce"
    )

    # Convert rating into numeric value
    df["Rating"] = pd.to_numeric(
        df["Rating"],
        errors="coerce"
    )

    return df


# ============================================================
# SAVE DATA
# ============================================================

def save_data(df):

    if df.empty:

        print("No data available to save.")

        logger.warning(
            "No data was available to save."
        )

        return

    df.to_csv(
        OUTPUT_FILE,
        index=False,
        encoding="utf-8"
    )

    print(
        f"\nData successfully saved to: {OUTPUT_FILE}"
    )

    print(
        f"Total products scraped: {len(df)}"
    )

    logger.info(
        f"Saved {len(df)} products to {OUTPUT_FILE}"
    )


# ============================================================
# PROGRAM ENTRY POINT
# ============================================================

if __name__ == "__main__":

    try:

        products = scrape_website()

        cleaned_data = clean_data(
            products
        )

        save_data(
            cleaned_data
        )

        print(
            "\n========== SCRAPING COMPLETED =========="
        )

    except KeyboardInterrupt:

        print(
            "\nScraping interrupted by user."
        )

        logger.warning(
            "Scraping interrupted by user."
        )

    except Exception as error:

        print(
            f"\nUnexpected error: {error}"
        )

        logger.exception(
            "Unexpected error occurred."
        )
        