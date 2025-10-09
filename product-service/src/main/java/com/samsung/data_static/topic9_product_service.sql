-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 09, 2025 at 04:59 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `topic9_product_service`
--

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `id` varchar(255) NOT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `price_at_time` bigint(20) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`id`, `order_id`, `price_at_time`, `product_id`, `quantity`, `status`) VALUES
('0285d2eb-e6c7-4bc3-bfc9-91a0b5f3f370', '1ead1737-452b-47df-bb0b-426691e73b5f', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('04ab7ac6-4098-4ebc-a0d6-1fb13eda856c', '63f9630e-396e-410f-b753-60a34fffcaa3', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('0746e81a-535c-484c-8297-847a5e252212', '7f065321-6537-460f-a3d4-d8ef8f64a571', 350000, '3adcd20b-d6b1-4f2d-969b-dc469e1df170', 1, NULL),
('0983e088-39cd-4594-bbfe-b20bf27bfad7', '8261c7e0-a304-46d9-9749-dc8c0598dbdd', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('106df7b9-2a8d-40f8-abe2-edd131d2093e', 'e86b702c-9b31-4933-95a2-cb77d3b6fa64', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 2, NULL),
('10dd6c4d-3aec-42d4-9c00-27c1babb3e7a', '76badf35-558c-4e52-99ec-7188b86a9cf4', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('11074fce-0ea9-4a3a-b672-4557210a047a', 'c3cc9d21-a9d2-406c-a404-3e0bfd2287ed', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('12a8d903-ba24-4e69-b52a-6c34fae8c3ea', '00ff7431-af26-4f67-af88-57772f6a4b26', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 4, NULL),
('14d7dce0-49ab-47f2-84da-c1c4c09973c7', '10b94688-7602-426f-8c7a-d5ea88b6b779', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('1a92a348-fdcb-4243-8fd7-b7413c85c0df', '99c9f081-6de0-492a-9676-7753cf77ab4b', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('1b0b65d7-9241-41f6-8eb0-f5cc5f329021', '2dd2486f-df2d-4acb-8527-55c0ccb1d3f8', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('1c2bf3ff-b87d-4f42-8479-af03b31f117f', '75dc9302-2a66-4616-b505-1e004be69785', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('1d4dec57-5158-4bc0-b25a-201c16d31754', '3f6c2f57-301d-4388-bf65-1e344755c3a2', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('1ef82107-8ee6-4f71-b607-7b66046ca0bd', '2dd25cca-81a8-4778-aec8-e759a7432a37', 30000, '4f84cdd8-3160-42fb-bf6a-6e040b497d63', 1, NULL),
('1f12aa1c-6201-4cbc-9082-9e32cfc56dfe', '88719c4d-d2df-41ca-92ae-2bc3a5e590bb', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('1fe7df41-0291-469f-8d08-045c58a87341', '7f1717e6-8937-42b0-add9-dd7a64a2ff34', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('24ec8c12-df3d-442d-ab17-bd35124654d2', '4f475db0-fc79-4729-a4c5-2e4712771566', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('259fcbfc-e4a1-41b3-b143-edd14ed92597', '4303acdf-6ef5-44d8-9b56-bc8e2df57119', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('2e11695f-22d8-409d-adb1-f4abb77020b3', 'a8465c08-edc8-4612-9f79-8d4c05e99966', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('312b32e8-ae5c-48c4-99ff-5f6c4ad71346', '6a892c6a-08ea-40fa-a583-48d6c8d2f081', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('3657d801-4e03-4437-a757-98722cdf62fb', '4f475db0-fc79-4729-a4c5-2e4712771566', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 2, NULL),
('431e3f3a-7d08-439b-a616-7c8bb21f5f9b', '89522fc7-0f56-41ca-9b76-9093abd841ec', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 2, NULL),
('4424b910-afa7-4936-9ea6-634e36fbd242', '4c1e21d0-708f-4f11-9125-2ff9b4e81fd9', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('44c282fa-2a8c-481e-ba74-77548cb98e7e', 'aa957993-2dfb-46e3-88ee-0a10d534a7fd', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('4682e17d-9a34-4363-b30f-209c5df62070', 'dcac459f-fa86-4039-aa58-58dd349ff6d5', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('486c9efb-ab1d-473b-bfa9-de1205f3d234', '7c22992c-faf8-4f15-a1b7-7bd8dd8fc7e1', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('48a84ea4-c844-4d3e-949f-28c6d47dc9d6', '8d3d9d55-7cb3-4406-adc3-ad70d7a3521d', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('49e8daa9-7b23-4bb9-90a2-de4b2eee378b', '436e9d05-7ad6-49dc-9f6f-3c53badf9b0a', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('4bfd7ab5-b360-4422-bfd3-d17aa2872f7c', 'e06eb5f5-dbf0-44d8-bb4a-c0ea55877573', 20000000, '2300aa90-9fb3-4a0e-b582-c5cef3eeb76a', 1, NULL),
('4dede295-4e83-43b1-9170-61f763a9a6d1', 'aa22ecfd-0d65-4c0c-9064-21485d7449e3', 20000, '3adcd20b-d6b1-4f2d-969b-dc469e1df170', 1, NULL),
('4f4fe6a7-5013-427a-8717-22737531f342', '11aeb858-ea65-41fc-80b3-3ccf4fd1ad68', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('4fa50a52-0e36-4b82-9fbb-bad7dd4f0767', '11aeb858-ea65-41fc-80b3-3ccf4fd1ad68', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('50b3bd87-386b-4104-b2c1-57fb2d63ca2a', '7f065321-6537-460f-a3d4-d8ef8f64a571', 20000000, '2300aa90-9fb3-4a0e-b582-c5cef3eeb76a', 1, NULL),
('50caad3d-2632-457f-9c3b-df514e4d51c7', '99c9f081-6de0-492a-9676-7753cf77ab4b', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 2, NULL),
('553c9042-d9ed-47fc-86a1-95a9b7816190', '2e003013-b21a-4733-a7fa-02d1c212568c', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 2, NULL),
('563798e3-a66e-4fbf-9484-0256d78e7336', '7c22992c-faf8-4f15-a1b7-7bd8dd8fc7e1', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('58eb0b59-3162-40eb-b0db-99baf56db647', '7f1717e6-8937-42b0-add9-dd7a64a2ff34', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('59351cc1-fc44-40df-89ea-e00d035e02da', '96b20e57-1c0c-40ec-8a5c-2bbf5770322b', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('5e23c17e-149e-43b9-a72e-b8c995d316e9', '3df1ed19-e95e-456c-995d-c3f0f2cbba61', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('5f3c2596-0039-4125-a476-4b6132348a03', 'a15bd6ac-be81-40a3-8636-0ad440e23958', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('6317d164-34b0-456d-8084-3f8c5e515010', '3df1ed19-e95e-456c-995d-c3f0f2cbba61', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('6391939b-789e-4d12-bc85-dc8c499ac76d', 'c7b0828f-5234-42b5-aa87-e7bc9a28bc92', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('64341e38-6ea2-4eab-ae5c-38ecc4c940e1', '585436a0-328e-4581-8a4f-bfcf08ceae39', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('696b0fe4-8955-4944-9b95-76ecccebcc11', '1ad5b5e1-7a5b-489e-a847-660efa6c9dee', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('69c5e86c-28e8-4ea2-b0a0-edf77c823c58', 'dcac459f-fa86-4039-aa58-58dd349ff6d5', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('6be5d079-8242-46c7-8d32-60e31d15bf3e', '4c1e21d0-708f-4f11-9125-2ff9b4e81fd9', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('747e3f7b-e7e1-451c-b331-c5bf9b08a5cb', '96b20e57-1c0c-40ec-8a5c-2bbf5770322b', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('78f1adb6-a73f-4f24-a99c-ff9017e5a68b', '8261c7e0-a304-46d9-9749-dc8c0598dbdd', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('79c896b0-b425-420f-9041-cbf79a8366bb', 'b6463d1d-0572-4fed-acde-a1ad0f7570a4', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('7bddea5a-4d55-47c8-aea8-254c1b7e9b77', '88719c4d-d2df-41ca-92ae-2bc3a5e590bb', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('7f017ac1-ba8c-45b5-b53c-8caac3547af6', 'a15bd6ac-be81-40a3-8636-0ad440e23958', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('810054e7-23d0-4350-9b04-3ce24243e33a', 'd609e058-1fe9-4aa9-823c-16624c7a1a11', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('86b40016-f3e6-4439-a0ab-2a13af17ba77', '17f2c3cb-2832-4f89-95a3-8a877b249b1f', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('86b66b10-e584-4a70-86e7-31eda805e38f', '79cbe74d-86b5-45d7-a797-9bc72b322708', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('889838c2-31c3-4122-9b2a-ef160f2519d8', 'aa957993-2dfb-46e3-88ee-0a10d534a7fd', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('88ad3f78-9396-4d87-859c-0ac38769ad2c', 'f2dc1645-6c5b-4222-98d7-9ff42c670f9b', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('8b770a38-6204-4733-8300-64e9e3682a5e', '2dd2486f-df2d-4acb-8527-55c0ccb1d3f8', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('8e1e6a62-0eaf-46cb-9738-d73e408fdafe', '54306289-b9cb-4f56-85bd-9c97795b4b74', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('8f850f08-5504-4081-a491-dbbf65ad1134', '585436a0-328e-4581-8a4f-bfcf08ceae39', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('91097f8f-878b-4efa-84ff-3bf95b4aed12', '63f9630e-396e-410f-b753-60a34fffcaa3', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('916af94b-076e-49b7-ad14-56c7651a3c5d', '4303acdf-6ef5-44d8-9b56-bc8e2df57119', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('917718ff-05bc-426b-8abf-18dd4225035d', '7498ef00-1afb-4ef0-9ecd-1abcbe6542e8', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('91d6733f-3c03-467e-ae40-63d4b7957403', '99c9f081-6de0-492a-9676-7753cf77ab4b', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('928f6a05-2f75-4f55-8330-600313d221a4', '3df1ed19-e95e-456c-995d-c3f0f2cbba61', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('929feee0-d903-43b1-bc5e-5591aebf1828', '884d4da8-b06a-4ea0-a7c6-0439b4758fb6', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('980dab4e-a566-49f9-8720-a0bb6edcd7ea', '585436a0-328e-4581-8a4f-bfcf08ceae39', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 2, NULL),
('9b758c00-6e21-4155-ba0c-9f173227b6bf', 'c3cc9d21-a9d2-406c-a404-3e0bfd2287ed', 20000000, '2300aa90-9fb3-4a0e-b582-c5cef3eeb76a', 3, NULL),
('a534b34f-5f9d-4042-a647-ae307f827ffa', 'be057f4f-70a1-4862-b374-ac73c732812f', 350000, '3adcd20b-d6b1-4f2d-969b-dc469e1df170', 1, NULL),
('a55b57e0-69a4-4ef1-9263-8782b83ae66c', 'b90d57d0-e95e-4d90-864c-94a9faccf1ff', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 2, NULL),
('a5d96707-977c-4f53-b1cf-254672691a90', '1067df52-4fad-4f12-b507-91df9e061ad5', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('ae4f5b7f-1450-4f24-9fe4-116f7ac47c32', 'c7b0828f-5234-42b5-aa87-e7bc9a28bc92', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('afad86d8-d074-444c-97eb-da530cc2bee4', 'd5ad1fb7-0c26-4f54-906b-7822ec46a8f1', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('afe11c27-434e-48c4-b751-3a7730c64cf7', '9b129729-9b06-4721-b4c2-2b5ddb57d8ff', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('b116ba87-b971-45d0-b60c-20bf9a56b64e', 'a8e8c66a-83ee-446f-bed4-08b4e52997cf', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('b13e7a29-7f18-45e4-b271-725a7442c7b6', '63f9630e-396e-410f-b753-60a34fffcaa3', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('b66c7463-9417-44d8-bc6d-c296ae0a7d0c', 'be057f4f-70a1-4862-b374-ac73c732812f', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('b7598ad6-612c-4131-8e7e-8cf9a2c4b682', '7498ef00-1afb-4ef0-9ecd-1abcbe6542e8', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('b7ad4389-c3f1-4b4a-a422-d6b692aeecec', '99c61d2b-5fcf-4738-ba8f-d1b4870dfda0', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('bac9c7e7-4238-4fa4-b100-899aaff04338', 'dcac459f-fa86-4039-aa58-58dd349ff6d5', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('bfe6ca38-55d2-4658-b113-e6edaae06cf3', '4f475db0-fc79-4729-a4c5-2e4712771566', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('c03248dd-f421-422d-b094-49e77172f1fd', 'be057f4f-70a1-4862-b374-ac73c732812f', 20000000, '2300aa90-9fb3-4a0e-b582-c5cef3eeb76a', 2, NULL),
('c12ba3af-0622-4ed6-939e-5c069fd8f761', 'f7331691-17bc-456c-bb1c-9f93e6c3582d', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('c23bfe8d-401d-4e09-93fd-127805eb2344', '79cbe74d-86b5-45d7-a797-9bc72b322708', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('c30e0ce8-2376-4661-b520-ed636294a322', '09249fab-c7fe-4565-8069-343204e08de7', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('c386e8fc-59d1-48cc-a549-7d97ad24c450', '585436a0-328e-4581-8a4f-bfcf08ceae39', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('c3ee0c53-aa59-4035-bc34-721d9c8c6293', '4908daff-164d-4d26-ae30-9a2778d84d2b', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('c48cf701-48e3-4502-9d73-4089a4aa1ce5', '33e2ea1e-61d1-4e8f-af2d-7ee015f8b03c', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 2, NULL),
('c75dbd43-daf6-41c2-ac50-b4586ebcda62', '884d4da8-b06a-4ea0-a7c6-0439b4758fb6', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('c7ea07d1-c8df-447c-a8ed-ffa75a664d5f', '884d4da8-b06a-4ea0-a7c6-0439b4758fb6', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('c9e44703-c892-4120-9b5a-758f7b0db99b', '59ffd3da-9bf1-4752-b82d-701ad20a8be1', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 2, NULL),
('ccb84c25-6e51-486d-b06b-ca9ef6d3f886', '10b94688-7602-426f-8c7a-d5ea88b6b779', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('ccfec50e-1560-465e-8dee-5abdcff02ec9', '465b506a-d91b-45e4-883a-c1894504bbb0', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('cf351a59-27b4-4261-b139-aeb958499da3', '17f2c3cb-2832-4f89-95a3-8a877b249b1f', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('d01b4144-3f69-45cc-93cf-66e40e5a9f34', '9b129729-9b06-4721-b4c2-2b5ddb57d8ff', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('d05e1195-65a5-496a-a4ec-b6020ca3d763', '906c288b-72f0-4f81-bc73-7705bab67f72', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('d1b800b6-4f84-422b-bf86-a61545d1cef3', '96c83ba6-2ad9-4753-b7af-1ed974a2cd1a', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('d3a4b8d6-bb45-4212-97f5-b2b290d597f7', '82063262-d470-4ba5-81f6-9ac1ee43018d', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('d4d2832d-cac9-4f85-9328-c437635501e7', 'aa22ecfd-0d65-4c0c-9064-21485d7449e3', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('d7943f02-3663-4bb8-b52d-c48c296341a7', '87919dad-cded-4ea6-b75f-29e394c5e0c6', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 2, NULL),
('db6317ba-b122-4d08-a386-cfe1abc53732', '75dc9302-2a66-4616-b505-1e004be69785', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('dd9782ab-73b7-4ff7-a29e-734b24b0927c', 'c506ac44-6637-4f47-b61e-01545548591e', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('e859990a-1bf7-485d-b7a6-6e4a73f73ed9', '1ad5b5e1-7a5b-489e-a847-660efa6c9dee', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 1, NULL),
('e9e1d864-da12-4657-8214-468cd6073bf6', 'cd741dff-7afa-4b66-b262-238bec124254', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('eaa8ef6c-26c9-4174-bc34-8d7dcf75308b', '4908daff-164d-4d26-ae30-9a2778d84d2b', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('efb01381-92a2-44ad-a638-511a8d0d853d', 'b6463d1d-0572-4fed-acde-a1ad0f7570a4', 35000000, '3494eda3-628a-40ec-aa32-cc770d23bc1a', 1, NULL),
('f4472dd3-cca2-4426-bdeb-d71043ea7e1b', '536c6895-bdab-4e6d-9aca-09bea7e6584d', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 2, NULL),
('f64e6b14-d526-4ad5-834e-5d80a0a1922b', 'b90d57d0-e95e-4d90-864c-94a9faccf1ff', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 2, NULL),
('f79721df-1a04-4041-84d6-1c59d58f7489', 'd5ad1fb7-0c26-4f54-906b-7822ec46a8f1', 22000000, '7920445c-b0a2-489e-8dfc-1b10d184b439', 1, NULL),
('fb52c792-bd77-48fd-9882-2e1572fb2d29', 'beada82f-8108-4e87-9920-37bb19f09f50', 22000000, '94dfdb86-7d33-4f03-8ac6-38759a896d06', 1, NULL),
('fd2f0fd4-d6d8-42e0-acca-5d1cbda5cec1', '1b80a590-572d-40d0-9fc5-83a63f034341', 22000000, '4d332829-8ac5-400c-8131-eb99d269612b', 3, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `stock_quantity` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `reserved_stock` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `description`, `image_url`, `name`, `price`, `stock_quantity`, `type`, `reserved_stock`) VALUES
('2300aa90-9fb3-4a0e-b582-c5cef3eeb76a', 'rất mượt', '880bac25-bcb1-493b-b45d-94ed4c2739bd_s24.jfif', 'Samsung S24 ultra', 20000000, 100, 'Điện thoại', 5),
('3494eda3-628a-40ec-aa32-cc770d23bc1a', 'Samsung S25 untra', 'a2153485-7a67-4440-836f-c8a9f6a6e97b_default.jpg', 'Samsung S25 untra', 35000000, 15, 'Laptop', 14),
('3adcd20b-d6b1-4f2d-969b-dc469e1df170', 'mượt lắm', '4bd77f38-de7f-4a47-be6d-1516694da05f_img1.jfif', 's23 ultra', 350000, 41, 'Điện thoại', 2),
('4d332829-8ac5-400c-8131-eb99d269612b', 'Laptop core i7, 16GB', '2fc095cc-c8ab-4ffc-8f89-5942675addde_img1.jfif', 'Laptop Dell Inspiron', 22000000, 100, 'Laptop', 42),
('4f84cdd8-3160-42fb-bf6a-6e040b497d63', 'áđâsdá', '4611d235-a70b-4843-845e-b4693898cf1a_s24.jfif', 'Nguyen_Trong_Tan23', 30000, 16, 'Điện thoại', 1),
('544e2516-3786-4c20-8ee3-8e361f8519b7', 'AT180243 - Nguyễn Trọng Tấn', 'c5fc49b2-a660-4ff1-ac22-4b2ef801b3fe_AT180243_TOEIC.jpg', 'AT180243 - Nguyễn Trọng Tấn', 40000, 23, 'Điện thoại', 0),
('63a9c2ca-7941-434e-8747-c91b6829b665', 'áđâsdá', '7f1db2cd-9deb-47ad-b62d-879765a846dd_s24.jfif', 'Nguyen_Trong_Tan2', 30000, 16, 'Điện thoại', 0),
('70f4ffea-2c0b-4a61-80bb-522f5e6d6b22', 'sdfsdfsdf', 'ffc0633e-33a0-4921-8560-642274bdf125_AT180243_TOEIC.jpg', 'test1', 1000, 100, 'Điện thoại', 0),
('71f4ff42-a585-4488-b820-d07153c4c6fb', 'áđá', 'cdb43d66-1cc0-4a29-b4fa-208d1cc4685f_img1.jfif', 'Nguyen_Trong_Tan', 10000000, 10, 'Điện thoại', 0),
('7920445c-b0a2-489e-8dfc-1b10d184b439', 'Laptop core i7, 16GB', '09ccbd59-d047-4f1d-b3df-caba3246d2b7_img2.jfif', 'Laptop Dell Inspiron 3', 22000000, 150, 'Laptop', 47),
('94dfdb86-7d33-4f03-8ac6-38759a896d06', 'Laptop core i7, 16GB', 'f49d4a28-8099-4c39-96ec-3fffe4cea808_img2.jfif', 'Laptop Dell Inspiron 2', 22000000, 100, 'Laptop', 34),
('9e782e38-8bd3-4020-bf5c-3d3398b53254', 'sdcsdf', 'f4629de7-116d-4d2c-a1ba-1f47213a4fb3_s24.jfif', 'test123', 10000, 11, 'Điện thoại', 0),
('b63d3ca4-393f-4fcf-9fe6-8a4fbc77c98e', 'gdfgdfgdfg', '8c71f4ea-f0ca-4fae-b110-1e5e8ebc77b7_img1.jfif', 'sdfsdfdfdfgdfg', 20000, 14, 'Điện thoại', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
