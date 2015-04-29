#include <math.h>
#include <fstream.h>
#include <opencv2/opencv.hpp>
#define MAX
#include "array.h"
    /*1.高斯滤波对图像去噪。
	2.由原始灰度图求出纵横2个梯度图，以及综合梯度图（求梯度的算子很多）。
	3.结合3个 梯度图来进行非极大抑制（此步一过，检测的图像边缘已经很细了）
	4.进行边缘连接（强边缘到 弱边缘的连接，所有可能连接的点都出不了非极大抑制后的范围，在非极大抑制的结果中去除假边缘保留真边缘，同时又能让保留的 边缘尽量连贯真实）
	5.对边缘进行细化
	*/
/* Scale floating point magnitudes and angles to 8 bits */
#define ORI_SCALE 40.0
#define MAG_SCALE 20.0

/* Biggest possible filter mask */
#define MAX_MASK_SIZE 20

/* Fraction of pixels that should be above the HIGH threshold */

float ratio = 0.1;
int WIDTH = 0;
typedef uc2D IMAGE;


float gauss(float x, float sigma);
float dGauss (float x, float sigma);
float meanGauss (float x, float sigma);

//寻找边界起点
int range (IMAGE& im, int i, int j);
int trace (int i, int j, int low, IMAGE& im,IMAGE& mag, IMAGE& ori);
//统计直方图，判定阀值
void estimate_thresh (IMAGE& mag, int *low, int *hi);
void hysteresis (int high, int low, IMAGE& im, IMAGE& mag, IMAGE& oriim);



//卷积计算
void seperable_convolution (IMAGE& im, float *gau, int width,
		f2D& smx, f2D& smy);
void dxy_seperable_convolution (f2D& im, int nr, int nc, float *gau,
		int width, f2D& sm, int which);

//非最大值抑制
void nonmax_suppress (f2D& dx, f2D& dy, int nr, int nc,
		IMAGE& mag, IMAGE& ori);

void canny (float s, IMAGE& im, IMAGE& mag, IMAGE& ori);


const double M_PI=3.14159265358979323846;
const double ZER_INFINITY=1.22e10;
const double ZER_TINY=1e-6;

const int ZER_NO_MEMORY=-10;
const int ZER_ERROR=-1;
const int ZER_OK=0;

/** *  zer_pol() computes the zernike basis function *   
 V(n,m,x,y). 
 * @return res[1] is the dcomplex for V(n,m,x,y)*/

int zer_pol(int m,int n,double x,double y,