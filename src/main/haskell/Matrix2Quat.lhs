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
              False -> error "asd" -- more to come
\end{code}
