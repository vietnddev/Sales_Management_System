CREATE OR REPLACE VIEW VW_STORAGE_ITEMS AS
WITH PRODUCT_ITEM AS (
    SELECT
        'Y' AS IS_PRODUCT,
        pd.ID,
        CONCAT(CONCAT(fs.DIRECTORY_PATH, '/'), fs.SAVED_NAME) AS ITEM_IMAGE_SRC,
        pd.VARIANT_NAME AS NAME,
        cpt.NAME AS ITEM_TYPE,
        cbr.NAME AS BRAND_NAME,
        pd.QUANTITY_STG - pd.QUANTITY_DEFECTIVE AS AVAILABLE_QTY,
        pd.QUANTITY_STG AS STORAGE_QTY,
        (SELECT CREATED_AT FROM PRODUCT_DETAIL_TEMP WHERE PRODUCT_VARIANT_ID = pd.ID ORDER BY CREATED_AT ASC FETCH NEXT 1 ROW ONLY) AS FIRST_IMPORT_TIME,
        (SELECT CREATED_AT FROM PRODUCT_DETAIL_TEMP WHERE PRODUCT_VARIANT_ID = pd.ID ORDER BY CREATED_AT DESC FETCH NEXT 1 ROW ONLY) AS LAST_IMPORT_TIME,
        ti.STORAGE_ID
    FROM
        PRODUCT_DETAIL pd
    LEFT JOIN FILE_STORAGE fs ON
        fs.PRODUCT_VARIANT_ID = pd.ID
        AND fs.IS_ACTIVE = 1
    INNER JOIN PRODUCT p ON
        p.ID = pd.PRODUCT_ID
    LEFT JOIN CATEGORY cpt ON
        cpt.ID = p.PRODUCT_TYPE_ID
    LEFT JOIN CATEGORY cbr ON
        cbr.ID = p.BRAND_ID
    INNER JOIN PRODUCT_DETAIL_TEMP pt ON
        pt.PRODUCT_VARIANT_ID = pd.ID
    INNER JOIN TICKET_IMPORT_GOODS ti ON
        ti.ID = pt.GOODS_IMPORT_ID
),
MATERIAL_ITEM AS (
    SELECT
        'N' AS IS_PRODUCT,
        m.ID,
        CONCAT(CONCAT(fs.DIRECTORY_PATH, '/'), fs.SAVED_NAME) AS ITEM_IMAGE_SRC,
        m.NAME,
        '' AS ITEM_TYPE,
        cbr.NAME AS BRAND_NAME,
        0 AS AVAILABLE_QTY,
        m.QUANTITY AS STORAGE_QTY,
        (SELECT CREATED_AT FROM MATERIAL_TEMP WHERE MATERIAL_ID = m.ID ORDER BY CREATED_AT ASC FETCH NEXT 1 ROW ONLY) AS FIRST_IMPORT_TIME,
        (SELECT CREATED_AT FROM MATERIAL_TEMP WHERE MATERIAL_ID = m.ID ORDER BY CREATED_AT DESC FETCH NEXT 1 ROW ONLY) AS LAST_IMPORT_TIME,
        ti.STORAGE_ID
    FROM
        MATERIAL m
    LEFT JOIN FILE_STORAGE fs ON
        fs.MATERIAL_ID = m.ID
        AND fs.IS_ACTIVE = 1
    LEFT JOIN CATEGORY cbr ON
        cbr.ID = m.BRAND_ID
    INNER JOIN MATERIAL_TEMP mt ON
        mt.MATERIAL_ID = m.ID
    INNER JOIN TICKET_IMPORT_GOODS ti ON
        ti.ID = mt.GOODS_IMPORT_ID
),
STORAGE_ITEM AS (
    SELECT * FROM PRODUCT_ITEM
    UNION ALL
    SELECT * FROM MATERIAL_ITEM
)
SELECT DISTINCT
    IS_PRODUCT,
    ID,
    CASE
        WHEN ITEM_IMAGE_SRC = '/' THEN ''
        ELSE ITEM_IMAGE_SRC
        END AS ITEM_IMAGE_SRC,
    NAME,
    ITEM_TYPE,
    BRAND_NAME,
    AVAILABLE_QTY,
    STORAGE_QTY,
    FIRST_IMPORT_TIME,
    LAST_IMPORT_TIME,
    STORAGE_ID
FROM
    STORAGE_ITEM
WHERE
    STORAGE_QTY > 0
ORDER BY
    IS_PRODUCT DESC;