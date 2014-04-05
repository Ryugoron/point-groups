Module that converts 3x3 rotation matrices to the
according quaternion representation.

Use the function |toQuat| to calculate the quaternion of a |Matrix|.

\begin{code}
module Matrix2Quat where

data Matrix = M { m00 :: Double, m01 :: Double, m02 :: Double,
                  m10 :: Double, m11 :: Double, m12 :: Double,
                  m20 :: Double, m21 :: Double, m22 :: Double }
                deriving (Eq, Show, Read)

type Quaternion = (Double, Double, Double, Double)

toQuat :: Matrix -> Quaternion
toQuat m = let t = m00 m + m11 m + m22 m + 1 in
            case t > 0.00000001 of
              True -> let s = sqrt(t) * 2
                          x = (m21 m - m12 m) / s
                          y = (m02 m - m20 m) / s
                          z = (m10 m - m01 m) / s
                          w = 0.25 * s
                      in (x,y,z,w)
              False -> case maximum $ zip [m00 m, m11 m, m22 m] [3,2,1] of
                        (_,3) -> let s = sqrt(1.0 + m00 m - m11 m - m22 m) * 2
                                     w = (m21 m - m12 m) / s
                                     z = (m02 m + m20 m) / s
                                     y = (m10 m + m01 m) / s
                                     x = 0.25 * s
                                 in (x,y,z,w)
                        (_,2) -> let s = sqrt(1.0 + m11 m - m00 m - m22 m) * 2
                                     z = (m21 m + m12 m) / s
                                     w = (m02 m - m20 m) / s
                                     x = (m10 m + m01 m) / s
                                     y = 0.25 * s
                                 in (x,y,z,w)
                        (_,1) -> let s = sqrt(1.0 + m22 m - m00 m - m11 m) * 2
                                     y = (m21 m + m12 m) / s
                                     x = (m02 m + m20 m) / s
                                     w = (m10 m - m01 m) / s
                                     z = 0.25 * s
                                 in (x,y,z,w)
\end{code}
